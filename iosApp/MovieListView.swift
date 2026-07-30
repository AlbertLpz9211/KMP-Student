//
//  MovieListView.swift
//  
//
//  Created by Kevin Isai Garcia Parida on 30/07/26.
//

import SwiftUI
import Shared // Aquí importamos tu código de Kotlin

struct MovieListView: View {
    // 1. Creamos una instancia de tu Bridge
    private let bridge = IosMovieListBridge()
    
    // 2. Estado para que SwiftUI reaccione
    @State private var state: MovieListState?

    var body: some View {
        NavigationView {
            VStack {
                if let movies = state?.movies, !movies.isEmpty {
                    List(movies, id: \.id) { movie in
                        Text(movie.title)
                    }
                    .refreshable { bridge.refrescar() }
                } else if state?.isLoading == true {
                    ProgressView("Cargando desde Kotlin...")
                } else {
                    Text("No hay películas o error.")
                }
            }
            .navigationTitle("CineKMP Nativo")
            .onAppear {
                // 3. Iniciamos el bridge para recibir actualizaciones
                bridge.start { newState in
                    self.state = newState
                }
            }
            .onDisappear {
                // 4. Limpiamos para evitar fugas de memoria
                bridge.dispose()
            }
        }
    }
}
