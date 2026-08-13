import SwiftUI
import Shared

struct ListaView: View {
    @StateObject private var observable: ListaObservable

    init(viewModel: ListaViewModel) {
        _observable = StateObject(wrappedValue: ListaObservable(viewModel: viewModel))
    }

    var body: some View {
        NavigationView {
            VStack {
                TextField("Buscar artistas, canciones...", text: Binding(
                    get: { observable.state.query },
                    set: { observable.onQueryChange(q: $0) }
                ))
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding()

                ZStack {
                    if observable.state.cargando && observable.state.items.isEmpty {
                        ProgressView("Cargando...")
                    } else if observable.state.mostrarError {
                        ErrorView(error: observable.state.error) {
                            observable.onQueryChange(q: observable.state.query)
                        }
                    } else if observable.state.estaVacio {
                        Text("No se encontraron resultados.")
                            .foregroundColor(.secondary)
                    } else {
                        List(observable.state.items, id: \.id) { item in
                            ItemRow(item: item)
                        }
                        .refreshable {
                            observable.onQueryChange(q: observable.state.query)
                        }
                    }
                }
            }
            .navigationTitle("iTunes Search")
        }
    }
}

struct ItemRow: View {
    let item: Item

    var body: some View {
        HStack(spacing: 16) {
            AsyncImage(url: URL(string: item.imagenUrl ?? "")) { image in
                image.resizable()
                     .aspectRatio(contentMode: .fill)
            } placeholder: {
                Color.gray.opacity(0.3)
            }
            .frame(width: 60, height: 60)
            .cornerRadius(8)
            .clipped()

            VStack(alignment: .leading, spacing: 4) {
                Text(item.titulo)
                    .font(.headline)
                if let subtitulo = item.subtitulo {
                    Text(subtitulo)
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                }
                if !item.tags.isEmpty {
                    Text(item.tags.joined(separator: " • "))
                        .font(.caption)
                        .foregroundColor(.blue)
                }
            }
        }
        .padding(.vertical, 4)
    }
}

struct ErrorView: View {
    let error: AppError?
    let onRetry: () -> Void

    var body: some View {
        VStack(spacing: 16) {
            Image(systemName: "exclamationmark.triangle")
                .font(.system(size: 48))
                .foregroundColor(.amber)
            Text("¡Ups! Algo salió mal")
                .font(.title3)
            Text(error?.description ?? "Error desconocido")
                .multilineTextAlignment(.center)
                .padding(.horizontal)
            Button("Reintentar", action: onRetry)
                .buttonStyle(.borderedProminent)
        }
    }
}

extension Color {
    static let amber = Color(red: 1.0, green: 0.75, blue: 0.0)
}
