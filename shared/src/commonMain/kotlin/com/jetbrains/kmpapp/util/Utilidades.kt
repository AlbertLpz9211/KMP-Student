package com.jetbrains.kmpapp.util

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

// --- 1. Expects ---
expect fun epochMillis(): Long

expect class Reloj() {
    fun ahora(): Long
}

// --- 2. Corrutinas / Flow ---

// Flujo de búsqueda
@OptIn(FlowPreview::class)
fun buscarConRetraso(flujoEntrada: Flow<String>): Flow<String> {
    return flujoEntrada
        .debounce(300L)
        .distinctUntilChanged()
}

// StateFlow de un carrito
class Carrito {
    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items.asStateFlow()

    // Flujo derivado que da el total de ítems
    val totalItems: Flow<Int> = _items.map { it.size }

    fun agregar(item: String) {
        // Se crea una nueva lista con el item agregado
        _items.value = _items.value + item
    }

    fun quitar(item: String) {
        // Se crea una nueva lista sin el item
        _items.value = _items.value - item
    }
}