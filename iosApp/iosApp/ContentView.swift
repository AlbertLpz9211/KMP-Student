import SwiftUI
import Shared

// 1. EL PUENTE: Store que conecta SwiftUI con el Bridge de Kotlin
@MainActor
final class NativeMovieListStore: ObservableObject {
    @Published var state: MovieListState?
    @Published var query = ""
    private let bridge = IosMovieListBridge()

    init() {
        bridge.start { [weak self] newState in
            Task { @MainActor in
                self?.state = newState
            }
        }
    }

    deinit {
        bridge.dispose()
    }

    func updateQuery(_ value: String) {
        query = value
        bridge.onQueryChange(query: value)
    }

    func refresh() {
        bridge.refrescar()
    }

    func loadMoreIfNeeded(currentMovie movie: Movie) {
        guard let state, !state.peliculas.isEmpty, !state.enBusqueda else { return }
        if movie.id == state.peliculas[max(state.peliculas.count - 4, 0)].id {
            bridge.cargarMas()
        }
    }
}

// 2. VISTA PRINCIPAL: Las "dos caras" de la app
struct ContentView: View {
    var body: some View {
        TabView {
            // Cara A: Compose Multiplatform
            ComposeView()
                .ignoresSafeArea()
                .tabItem {
                    Label("Compose", systemImage: "square.grid.2x2")
                }

            // Cara B: SwiftUI Nativa
            NativeMovieListView()
                .tabItem {
                    Label("SwiftUI Nativo", systemImage: "apple.logo")
                }
        }
    }
}

// 3. PANTALLA NATIVA EN SWIFTUI (Usando NavigationView para compatibilidad)
struct NativeMovieListView: View {
    @StateObject private var store = NativeMovieListStore()

    var body: some View {
        NavigationView {
            Group {
                if let state = store.state {
                    NativeMovieListContent(state: state, store: store)
                } else {
                    ProgressView("Cargando desde Kotlin...")
                }
            }
            .navigationTitle("CineKMP Nativo")
            .searchable(text: Binding(
                get: { store.query },
                set: { store.updateQuery($0) }
            ), prompt: "Buscar películas")
        }
        .navigationViewStyle(.stack) // Evita problemas en iPad/pantallas grandes
    }
}

struct NativeMovieListContent: View {
    let state: MovieListState
    @ObservedObject var store: NativeMovieListStore
    private let columns = [GridItem(.adaptive(minimum: 150), spacing: 16)]

    var body: some View {
        if state.cargando || state.buscando {
            ProgressView().frame(maxWidth: .infinity, maxHeight: .infinity)
        } else if let error = state.error {
            NativeMessageView(title: "Error", systemImage: "exclamationmark.triangle", message: error)
        } else {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 18) {
                    ForEach(state.peliculas, id: \.id) { movie in
                        NativeMovieCard(movie: movie)
                            .onAppear { store.loadMoreIfNeeded(currentMovie: movie) }
                    }
                }.padding()
            }
        }
    }
}

struct NativeMovieCard: View {
    let movie: Movie
    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            AsyncImage(url: movie.posterUrl.flatMap(URL.init(string:))) { image in
                image.resizable().scaledToFill()
            } placeholder: {
                ZStack {
                    Color(.secondarySystemBackground)
                    Image(systemName: "film").foregroundStyle(.secondary)
                }
            }
            .frame(maxWidth: .infinity).aspectRatio(2/3, contentMode: .fit).clipShape(RoundedRectangle(cornerRadius: 12))
            
            Text(movie.titulo).font(.headline).lineLimit(2)
            Text("★ \(movie.rating, specifier: "%.1f") · \(movie.anio)").font(.caption).foregroundStyle(.secondary)
        }
    }
}

struct NativeMessageView: View {
    let title: String
    let systemImage: String
    var message: String? = nil
    var body: some View {
        VStack(spacing: 12) {
            Image(systemName: systemImage).font(.largeTitle).foregroundStyle(.secondary)
            Text(title).font(.headline)
            if let message { Text(message).font(.body).foregroundStyle(.secondary) }
        }.padding()
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
