package com.jetbrains.kmpapp.domain.model

enum class BookStatus {
    NONE,
    POR_LEER,
    LEYENDO,
    TERMINADO
}

data class Item(
    val id: String,
    val titulo: String,
    val subtitulo: String?,
    val imagenUrl: String?,
    val metrica: Double?,
    val fecha: String?,
    val tags: List<String>,
    val isFavorite: Boolean = false,
    val status: BookStatus = BookStatus.NONE,
)
