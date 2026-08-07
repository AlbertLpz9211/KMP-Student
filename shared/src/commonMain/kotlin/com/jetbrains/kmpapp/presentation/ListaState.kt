package com.jetbrains.kmpapp.presentation

import com.jetbrains.kmpapp.domain.model.AppError
import com.jetbrains.kmpapp.domain.model.Item

data class ListaState(
    val items: List<Item> = emptyList(),
    val cargando: Boolean = false,
    val error: AppError? = null,
    val query: String = ""
) {
    val estaVacio: Boolean get() = !cargando && items.isEmpty() && error == null
    val mostrarError: Boolean get() = error != null && items.isEmpty()
}
