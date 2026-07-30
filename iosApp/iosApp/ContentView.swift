import UIKit
import SwiftUI
import Shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(
        _ uiViewController: UIViewController,
        context: Context
    ) {}
}

struct MovieListView: View {
    private let viewModel =
        IosViewModelFactory().movieListViewModel()

    @State private var peliculas: [Movie] = []

    var body: some View {
        NavigationStack {
            List(peliculas, id: \.id) { pelicula in
                Text(pelicula.titulo)
            }
            .navigationTitle("Populares")
        }
        .task {
            viewModel.refrescar()

            // Recibe los cambios del StateFlow de Kotlin.
            for await estado in viewModel.state {
                peliculas = estado.peliculas
            }
        }
    }
}

struct ContentView: View {
    var body: some View {
        TabView {
            ComposeView()
                .ignoresSafeArea()
                .tabItem {
                    Label(
                        "Compose",
                        systemImage: "rectangle.on.rectangle"
                    )
                }

            MovieListView()
                .tabItem {
                    Label(
                        "SwiftUI",
                        systemImage: "swift"
                    )
                }
        }
    }
}