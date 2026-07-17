package com.jetbrains.kmpapp.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
data class ListaState(
    val cargando: Boolean = false,
    val personajes: List<String> = emptyList(),
    val error: String? = null
)

class BuscadorState(private val scope: CoroutineScope) {

    // (b) Flow para el texto de búsqueda
    private val _query = MutableStateFlow("")

    // (a) StateFlow que expone el estado de la lista
    private val _listaState = MutableStateFlow(ListaState())
    val listaState: StateFlow<ListaState> = _listaState.asStateFlow()

    init {
        setupSearchFlow()
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchFlow() {
        _query
            .debounce(300) // (b) Evita peticiones por cada tecla
            .distinctUntilChanged() // (b) No dispara si el texto no cambió
            .onEach { text ->
                if (text.isBlank()) {
                    _listaState.value = ListaState() // Reset si está vacío
                } else {
                    realizarBusqueda(text)
                }
            }
            .launchIn(scope)
    }

    fun onQueryChange(nuevoTexto: String) {
        _query.value = nuevoTexto
    }

    private suspend fun realizarBusqueda(query: String) {
        _listaState.value = _listaState.value.copy(cargando = true, error = null)

        try {
            // Simulación de llamada a red (RickMorty API)
            delay(500)
            val resultadosSimulados = listOf("Rick $query", "Morty $query", "Summer $query")
            _listaState.value = ListaState(personajes = resultadosSimulados, cargando = false)
        } catch (e: Exception) {
            _listaState.value = ListaState(error = "Error al cargar personajes", cargando = false)
        }
    }
}