import Foundation
import Shared
import Combine
import SKIESwift

@MainActor
class ListaObservable: ObservableObject {
    private let viewModel: ListaViewModel

    @Published var state: ListaState = ListaState(items: [], cargando: false, error: nil, query: "")

    private var task: Task<Void, Never>? = nil

    init(viewModel: ListaViewModel) {
        self.viewModel = viewModel

        // Usamos SKIE para recolectar el StateFlow como una AsyncSequence nativa de Swift
        self.task = Task {
            for await currentState in viewModel.state {
                self.state = currentState
            }
        }
    }

    func onQueryChange(q: String) {
        viewModel.onQueryChange(q: q)
    }

    deinit {
        // Cancelamos la tarea de recolección y limpiamos el viewModel
        task?.cancel()
        viewModel.onCleared()
    }
}
