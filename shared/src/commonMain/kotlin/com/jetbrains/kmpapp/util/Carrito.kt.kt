package com.jetbrains.kmpapp.util

import kotlinx.coroutines.flow.*

class Carrito {
    private val _items = MutableStateFlow(emptyList<String>())
    val items: StateFlow<List<String>> = _items.asStateFlow()

    val totalItems: Flow<Int> = _items.map { it.size }

    fun agregar(producto: String) {
        _items.update { it + producto }
    }

    fun quitar(producto: String) {
        _items.update { it - producto }
    }
}

