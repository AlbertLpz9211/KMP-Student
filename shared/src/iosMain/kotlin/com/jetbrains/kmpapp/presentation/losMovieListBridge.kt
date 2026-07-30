package com.jetbrains.kmpapp.presentation

// Importo el estado de la lista porque es lo que SwiftUI va a recibir para dibujar la pantalla.
import com.jetbrains.kmpapp.presentation.list.MovieListState
// Importo el ViewModel compartido porque la pantalla nativa de iOS debe usar la misma logica de Kotlin.
import com.jetbrains.kmpapp.presentation.list.MovieListViewModel
// Estas corrutinas sirven para escuchar el StateFlow del ViewModel desde iOS.
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
// Uso KoinComponent para pedir el ViewModel que ya esta registrado en la inyeccion de dependencias.
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// Este bridge es el puente entre SwiftUI y el MovieListViewModel de Kotlin.
class IosMovieListBridge : KoinComponent {
    // Pido el ViewModel a Koin para no crearlo a mano ni duplicar dependencias.
    private val viewModel: MovieListViewModel by inject()
    // Creo un scope en Main porque SwiftUI debe recibir los cambios de estado en el hilo principal.
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    // Guardo el Job para cancelar la recoleccion anterior si Swift vuelve a llamar start.
    private var stateJob: Job? = null

    // Esta funcion empieza a escuchar el StateFlow y manda cada nuevo estado a Swift.
    fun start(onState: (MovieListState) -> Unit) {
        stateJob?.cancel()
        stateJob = scope.launch {
            viewModel.state.collectLatest { state ->
                onState(state)
            }
        }
    }

    // Esta funcion recibe el texto del buscador de SwiftUI y se lo pasa al ViewModel compartido.
    fun onQueryChange(query: String) {
        viewModel.onQueryChange(query)
    }

    // Esta funcion conecta el pull-to-refresh de SwiftUI con la recarga de populares en Kotlin.
    fun refrescar() {
        viewModel.refrescar()
    }

    // Esta funcion conecta la paginacion de SwiftUI con la funcion cargarMas del ViewModel.
    fun cargarMas() {
        viewModel.cargarMas()
    }

    // Esta funcion libera el trabajo de corrutinas cuando la pantalla nativa ya no se usa.
    fun dispose() {
        stateJob?.cancel()
        scope.cancel()
    }
}
