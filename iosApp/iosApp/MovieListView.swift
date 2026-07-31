import SwiftUI
import Shared

struct MovieListView: View {
    // Variable de estado para guardar las películas que vienen de Kotlin
    @State private var peliculas: [Movie] = []

    // Motor de la App: Lo obtenemos de Koin (vía el helper en Kotlin)
    private let viewModel: MovieListViewModel = KoinHelper.shared.getMovieListViewModel()

    var body: some View {
        NavigationView {
            List {
                ForEach(peliculas, id: \.id) { movie in
                    HStack(alignment: .top, spacing: 12) {
                        // 1. Imagen del póster (Nativo SwiftUI AsyncImage)
                        if let urlString = movie.posterUrl, let url = URL(string: urlString) {
                            AsyncImage(url: url) { image in
                                image.resizable()
                                     .aspectRatio(contentMode: .fill)
                            } placeholder: {
                                Color.gray.opacity(0.3)
                            }
                            .frame(width: 60, height: 90)
                            .clipped()
                            .cornerRadius(8)
                        }

                        // 2. Información de la película
                        VStack(alignment: .leading, spacing: 4) {
                            Text(movie.titulo)
                                .font(.headline)
                                .lineLimit(2)
                            
                            Text(movie.anio)
                                .font(.subheadline)
                                .foregroundColor(.secondary)
                            
                            Spacer()
                            
                            HStack {
                                Image(systemName: "star.fill")
                                    .foregroundColor(.orange)
                                    .font(.caption)
                                Text(String(format: "%.1f", movie.rating))
                                    .font(.caption)
                                    .fontWeight(.bold)
                            }
                        }
                        .padding(.vertical, 4)
                    }
                }
            }
            .navigationTitle("Películas Populares")
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
