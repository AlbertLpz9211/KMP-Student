package com.jetbrains.kmpapp.screens.list

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

data class ListaState(
    val cargando: Boolean = false,
    val personajes: List<String> = emptyList(),
    val error: String? = null
)

class BuscadorState {
    private val _state = MutableStateFlow(ListaState())
    val state: StateFlow<ListaState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class)
    val debouncedQuery = _query
        .debounce(300)
        .distinctUntilChanged()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}
