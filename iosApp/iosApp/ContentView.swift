import UIKit
import SwiftUI
import Shared

// 1. El puente para ver la App de Compose (Compartida)
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

// 2. La vista principal con "Dos Caras"
struct ContentView: View {
    var body: some View {
        TabView {
            // Pestaña 1: La App completa hecha en Compose
            ComposeView()
                .ignoresSafeArea()
                .tabItem {
                    Label("Compose", systemImage: "paintpalette")
                }

            // Pestaña 2: La pantalla nativa hecha en SwiftUI
            MovieListView()
                .tabItem {
                    Label("Nativo Swift", systemImage: "applelogo")
                }
        }
    }
}
