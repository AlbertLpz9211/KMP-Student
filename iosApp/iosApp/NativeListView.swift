import SwiftUI
import Shared

class ListViewModelWrapper: ObservableObject {
    @Published var items: [Item] = []
    
    let viewModel: ListViewModel = ViewModelProvider.shared.getListViewModel()
    
    init() {
        HelperKt.observeListItems(viewModel: viewModel) { [weak self] newItems in
            DispatchQueue.main.async {
                self?.items = newItems
            }
        }
    }
}

struct NativeListView: View {
    @StateObject var wrapper = ListViewModelWrapper()
    
    var body: some View {
        NavigationView {
            List(wrapper.items, id: \.id) { item in
                HStack {
                    if let urlString = item.imagenUrl, let url = URL(string: urlString) {
                        AsyncImage(url: url) { image in
                            image.resizable()
                        } placeholder: {
                            Color.gray
                        }
                        .frame(width: 50, height: 75)
                        .cornerRadius(4)
                    }
                    
                    VStack(alignment: .leading) {
                        Text(item.titulo)
                            .font(.headline)
                        if let subtitulo = item.subtitulo {
                            Text(subtitulo)
                                .font(.subheadline)
                                .foregroundColor(.secondary)
                        }
                    }
                }
            }
            .navigationTitle("Libros (SwiftUI)")
        }
    }
}
