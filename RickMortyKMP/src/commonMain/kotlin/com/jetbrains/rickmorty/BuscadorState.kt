package com.jetbrains.rickmorty

import kotlinx.coroutines.flow.*

data class ListaState(
    val cargando: Boolean = false,
    val personajes: List<String> = emptyList(),
    val error: String? = null
)

class BuscadorState {
    private val _state = MutableStateFlow(ListaState())
    val state: StateFlow<ListaState> = _state.asStateFlow()

    @OptIn(kotlinx.coroutines.FlowPreview::class)
    fun filtrarBusqueda(queryFlow: Flow<String>): Flow<String> {
        return queryFlow
            .debounce(300)
            .distinctUntilChanged()
    }

    fun setCargando() {
        _state.value = _state.value.copy(cargando = true, error = null)
    }

    fun setPersonajes(personajes: List<String>) {
        _state.value = _state.value.copy(cargando = false, personajes = personajes, error = null)
    }

    fun setError(mensaje: String) {
        _state.value = _state.value.copy(cargando = false, error = mensaje)
    }
}
