package examen.rickmorty.domain

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

// (a) Data class de estado y StateFlow
data class ListaState(
    val cargando: Boolean = false,
    val personajes: List<Personaje> = emptyList(),
    val error: String? = null
)

class BuscadorState {
    private val _state = MutableStateFlow(ListaState())
    val state: StateFlow<ListaState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    // (b) Flow de texto de búsqueda con debounce y distinctUntilChanged
    @OptIn(FlowPreview::class)
    val searchQueryFlow = _searchQuery
        .filter { it.length >= 3 || it.isEmpty() }
        .debounce(300)
        .distinctUntilChanged()

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateState(newState: ListaState) {
        _state.value = newState
    }
}