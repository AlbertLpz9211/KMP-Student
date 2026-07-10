package com.jetbrains.kmpapp.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class Carrito {
    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items: StateFlow<List<String>> = _items.asStateFlow()

    // Flujo derivado que expone únicamente el total de ítems
    val totalItems: Flow<Int> = items.map { it.size }

    fun agregar(item: String) {
        _items.update { actual -> actual + item }
    }

    fun quitar(item: String) {
        _items.update { actual -> actual.filter { it != item } } // remueve todas las coincidencias
    }
}