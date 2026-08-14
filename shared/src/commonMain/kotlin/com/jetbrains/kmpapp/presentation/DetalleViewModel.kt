package com.jetbrains.kmpapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import com.jetbrains.kmpapp.domain.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalleState(
    val cargando: Boolean = false,
    val item: ItemDetalle? = null,
    val error: String? = null
)

class DetalleViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetalleState())
    val state: StateFlow<DetalleState> = _state.asStateFlow()

    fun cargarDetalle(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(cargando = true, error = null) }
            try {
                // Obtenemos la lista y buscamos el item
                val items = repository.observarCatalogo().first()
                val itemBase = items.find { it.id == id }
                
                if (itemBase != null) {
                    // Mapeamos a ItemDetalle (en un caso real esto vendría del repositorio/API)
                    val detalle = ItemDetalle(
                        id = itemBase.id,
                        titulo = itemBase.titulo,
                        subtitulo = itemBase.subtitulo,
                        imagenUrl = itemBase.imagenUrl,
                        metrica = itemBase.metrica,
                        fecha = itemBase.fecha,
                        tags = itemBase.tags,
                        descripcion = "Descripción detallada para ${itemBase.titulo}",
                        precio = "1.99",
                        moneda = "USD",
                        pais = "USA",
                        urlPreview = "https://example.com/preview.mp3"
                    )
                    _state.update { it.copy(cargando = false, item = detalle) }
                } else {
                    _state.update { it.copy(cargando = false, error = "Item no encontrado") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(cargando = false, error = e.message) }
            }
        }
    }
}
