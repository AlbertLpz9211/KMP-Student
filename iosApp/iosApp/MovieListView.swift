import SwiftUI
import Shared

struct MovieListView: View {
    // Variable de estado para guardar los títulos que vienen de Kotlin
    @State private var titulos: [String] = []

    // Obtenemos el ViewModel desde Koin (inyectado a través de la clase Helper de Shared)
    private let viewModel = MovieListViewModel(repository: MovieRepositoryImpl(remote: TmdbApi(apiKey: Config.shared.TMDB_API_KEY, engine: nil), local: MovieLocalDataSource(db: CineDb(driver: DriverFactory().createDriver()))))

    var body: some View {
        NavigationView {
            List(titulos, id: \.self) { titulo in
                Text(titulo)
            }
            .navigationTitle("Populares (SwiftUI)")
            .task {
                // Gracias a SKIE, podemos recorrer el StateFlow de Kotlin como una secuencia asíncrona de Swift
                // Aquí observamos el estado de las películas
                for await state in viewModel.uiState {
                    // Mapeamos la lista de objetos Movie a sus títulos (Strings)
                    self.titulos = state.movies.map { $0.title }
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
