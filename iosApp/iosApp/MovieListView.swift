import SwiftUI
import Shared

struct MovieListView: View {
    // Variable de estado para guardar las películas que vienen de Kotlin
    @State private var peliculas: [Movie] = []

    // Motor de la App: Construimos el ViewModel inyectando sus dependencias manualmente
    private let viewModel: MovieListViewModel = {
        let driver = DriverFactory().createDriver()
        let db = CineDb(driver: driver)
        let local = MovieLocalDataSource(db: db)
        let remote = TmdbApi(apiKey: Config.shared.TMDB_API_KEY, engine: nil)
        let repository = MovieRepositoryImpl(remote: remote, local: local)

        return MovieListViewModel(
            getPopulares: GetPopulares(repo: repository),
            buscarPeliculas: BuscarPeliculas(repo: repository)
        )
    }()

    var body: some View {
        NavigationView {
            List(peliculas, id: \.id) { movie in
                VStack(alignment: .leading) {
                    Text(movie.title)
                        .font(.headline)
                    HStack {
                        Text(movie.releaseYear)
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                        Spacer()
                        Text("★ \(String(format: "%.1f", movie.rating))")
                            .font(.caption)
                            .foregroundColor(.orange)
                    }
                }
            }
            .navigationTitle("CineKMP Native")
            .task {
                // Observamos el StateFlow 'state' de Kotlin usando la sintaxis AsyncSequence de SKIE
                for await currentState in viewModel.state {
                    self.peliculas = currentState.peliculas
                }
            }
        }
    }
}

struct MovieListView_Previews: PreviewProvider {
    static var previews: some View {
        MovieListView()
    }
}
