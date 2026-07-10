package com.jetbrains.kmpapp.util

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

/**
 * Filtro genérico para búsquedas.
 * Aplica debounce (espera) y evita duplicados consecutivos.
 */
@OptIn(FlowPreview::class)
fun <T> Flow<T>.searchFilter(): Flow<T> = this
    .debounce(300)
    .distinctUntilChanged()

/**
 * Carrito de compras genérico.
 * Puede guardar cualquier tipo de objeto (Peliculas, Strings, etc.)
 */
class Carrito<T> {
    private val _items = MutableStateFlow<List<T>>(emptyList())
    val items: StateFlow<List<T>> = _items.asStateFlow()

    // Flujo derivado que cuenta cuántos elementos hay
    val totalItems: Flow<Int> = items.map { it.size }

    fun agregar(item: T) {
        _items.update { it + item }
    }

    fun quitar(item: T) {
        _items.update { it - item }
    }

    fun limpiar() {
        _items.value = emptyList()
    }
}
