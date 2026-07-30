import SwiftUI
import Shared

class NativeViewModelWrapper: ObservableObject {
    @Published var state: MovieListState
    
    private let viewModel: MovieListViewModel = ViewModelProvider.shared.getMovieListViewModel()
    
    init() {
        // Estado inicial
        self.state = MovieListState(
            cargando: true,
            refrescando: false,
            cargandoMas: false,
            buscando: false,
            enBusqueda: false,
            peliculas: [],
            error: nil
        )
        
        // Observar el StateFlow de Kotlin
        KoinIosKt.observeMovieListState(viewModel: viewModel) { [weak self] newState in
            DispatchQueue.main.async {
                self?.state = newState
            }
        }
    }
}

struct NativeListView: View {
    @StateObject var wrapper = NativeViewModelWrapper()
    
    var body: some View {
        NavigationView {
            VStack {
                if wrapper.state.cargando && wrapper.state.peliculas.isEmpty {
                    ProgressView("Cargando populares...")
                } else {
                    List(wrapper.state.peliculas, id: \.id) { movie in
                        HStack {
                            if let urlString = movie.posterUrl, let url = URL(string: urlString) {
                                AsyncImage(url: url) { image in
                                    image.resizable()
                                } placeholder: {
                                    Color.gray
                                }
                                .frame(width: 50, height: 75)
                                .cornerRadius(4)
                            }
                            
                            VStack(alignment: .leading) {
                                Text(movie.titulo)
                                    .font(.headline)
                                Text(movie.anio)
                                    .font(.subheadline)
                                    .foregroundColor(.secondary)
                            }
                        }
                    }
                }
            }
            .navigationTitle("Populares (SwiftUI)")
            .onAppear {
                // Podríamos llamar a viewModel.refrescar() si quisiéramos forzar carga al aparecer
            }
        }
    }
}
