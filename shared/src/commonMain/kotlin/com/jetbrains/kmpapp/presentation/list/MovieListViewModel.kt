package com.jetbrains.kmpapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.domain.model.Movie
import com.jetbrains.kmpapp.domain.usecase.BuscarPeliculas
import com.jetbrains.kmpapp.domain.usecase.GetPopulares
import com.jetbrains.kmpapp.presentation.mensajeAmigable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Estado de la pantalla de LISTA. Cubre populares + búsqueda + paginación.
 */
data class MovieListState(
    val cargando: Boolean = false,      // carga inicial (sin datos aún)
    val refrescando: Boolean = false,   // pull-to-refresh
    val cargandoMas: Boolean = false,   // paginación infinita en curso
    val buscando: Boolean = false,      // búsqueda en curso
    val enBusqueda: Boolean = false,    // hay texto en el buscador (mostramos resultados, no populares)
    val peliculas: List<Movie> = emptyList(),
    val error: String? = null,
)

/** Estado interno de la búsqueda (resultado del flujo debounced). */
private data class Busqueda(
    val activa: Boolean = false,
    val cargando: Boolean = false,
    val resultados: List<Movie> = emptyList(),
    val error: String? = null,
)

/**
 * ViewModel de la lista con TRES capacidades:
 *  - Populares desde la DB (offline-first) + pull-to-refresh.
 *  - Paginación infinita: [cargarMas] baja la siguiente página y la fusiona en la DB.
 *  - Búsqueda con **debounce**: [onQueryChange] alimenta un Flow que espera 300 ms tras la
 *    última tecla (para no llamar a la red en cada letra) y consulta TMDB.
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MovieListViewModel(
    private val getPopulares: GetPopulares,
    private val buscarPeliculas: BuscarPeliculas,
) : ViewModel() {

    private val _refrescando = MutableStateFlow(false)
    private val _cargandoMas = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private val _query = MutableStateFlow("")
    private var pagina = 1

    // Flujo de búsqueda: reacciona al texto, con debounce y sin repetir consultas iguales.
    private val busqueda =
        _query
            .debounce(300)                 // espera a que el usuario deje de teclear
            .map { it.trim() }
            .distinctUntilChanged()        // no repetir la misma búsqueda
            .flatMapLatest { q ->          // cancela la búsqueda anterior si llega una nueva
                if (q.isEmpty()) {
                    flowOf(Busqueda(activa = false)) // sin texto → volvemos a populares
                } else {
                    flow {
                        emit(Busqueda(activa = true, cargando = true))
                        try {
                            emit(Busqueda(activa = true, resultados = buscarPeliculas(q)))
                        } catch (e: Exception) {
                            emit(Busqueda(activa = true, error = e.mensajeAmigable()))
                        }
                    }
                }
            }

    val state: StateFlow<MovieListState> =
        combine(getPopulares(), busqueda, _refrescando, _cargandoMas, _error) { populares, busq, refrescando, cargandoMas, error ->
            if (busq.activa) {
                // Modo búsqueda: mostramos los resultados de la red.
                MovieListState(
                    enBusqueda = true,
                    buscando = busq.cargando,
                    peliculas = busq.resultados,
                    error = if (busq.resultados.isEmpty()) busq.error else null,
                )
            } else {
                // Modo populares: mostramos la DB.
                MovieListState(
                    cargando = refrescando && populares.isEmpty(),
                    refrescando = refrescando,
                    cargandoMas = cargandoMas,
                    peliculas = populares,
                    error = if (populares.isEmpty()) error else null,
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieListState(cargando = true),
        )

    init {
        refrescar()
    }

    /** Texto del buscador (lo llama el TextField en cada tecla). */
    fun onQueryChange(nuevo: String) {
        _query.value = nuevo
    }

    /** Pull-to-refresh: recarga la primera página. */
    fun refrescar() {
        viewModelScope.launch {
            _refrescando.value = true
            _error.value = null
            try {
                pagina = 1
                getPopulares.refrescar()
            } catch (e: Exception) {
                _error.value = e.mensajeAmigable()
            } finally {
                _refrescando.value = false
            }
        }
    }

    /** Paginación infinita: baja la siguiente página (solo en modo populares). */
    fun cargarMas() {
        if (state.value.enBusqueda || _cargandoMas.value || _refrescando.value) return
        viewModelScope.launch {
            _cargandoMas.value = true
            try {
                getPopulares.cargarPagina(pagina + 1)
                pagina += 1
            } catch (e: Exception) {
                // Si falla una página extra, no rompemos la lista ya visible; solo lo ignoramos.
            } finally {
                _cargandoMas.value = false
            }
        }
    }
}
