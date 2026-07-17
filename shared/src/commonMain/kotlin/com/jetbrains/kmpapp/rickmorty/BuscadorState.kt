package com.jetbrains.kmpapp.rickmorty

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

data class ListaState(
    val cargando: Boolean = false,
    val personajes: List<String> = emptyList(),
    val error: String? = null
)

class BuscadorState {
    private val _state = MutableStateFlow(ListaState())
    val state: StateFlow<ListaState> = _state.asStateFlow()

    @OptIn(FlowPreview::class)
    fun filtrarBusqueda(queryFlow: Flow<String>): Flow<String> {
        return queryFlow
            .debounce(300)
            .distinctUntilChanged()
    }

    fun setCargando(cargando: Boolean) {
        _state.update { it.copy(cargando = cargando) }
    }

    fun setPersonajes(personajes: List<String>) {
        _state.update { it.copy(personajes = personajes, cargando = false, error = null) }
    }

    fun setError(error: String?) {
        _state.update { it.copy(error = error, cargando = false) }
    }
}
