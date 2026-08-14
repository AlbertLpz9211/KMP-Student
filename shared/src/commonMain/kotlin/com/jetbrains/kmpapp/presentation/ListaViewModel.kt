package com.jetbrains.kmpapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.domain.model.Resultado
import com.jetbrains.kmpapp.domain.repository.ItemRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ListaViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListaState())
    val state: StateFlow<ListaState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    
    private var searchJob: Job? = null

    init {
        // Observamos el catálogo local continuamente
        repository.observarCatalogo()
            .onEach { items ->
                _state.update { it.copy(items = items) }
            }
            .launchIn(viewModelScope)

        // Lógica de búsqueda con debounce
        _query
            .debounce(300L)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { q ->
                ejecutarBusqueda(q)
            }
            .launchIn(viewModelScope)

        // Búsqueda inicial por defecto
        onQueryChange("rock")
    }

    fun onQueryChange(q: String) {
        _query.value = q
        _state.update { it.copy(query = q, error = null) }
    }

    private fun ejecutarBusqueda(q: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null) }
            
            when (val resultado = repository.buscar(q)) {
                is Resultado.Exito -> {
                    _state.update { it.copy(cargando = false) }
                }
                is Resultado.Error -> {
                    _state.update { it.copy(cargando = false, error = resultado.error) }
                }
            }
        }
    }
}
