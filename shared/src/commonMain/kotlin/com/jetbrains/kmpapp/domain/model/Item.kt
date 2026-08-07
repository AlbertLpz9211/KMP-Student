package com.jetbrains.kmpapp.domain.model

data class Item(
    val id: String,
    val titulo: String,
    val subtitulo: String?,
    val imagenUrl: String?,
    val metrica: Double?,
    val fecha: String?,
    val tags: List<String>
)

data class ItemDetalle(
    val id: String,
    val titulo: String,
    val subtitulo: String?,
    val imagenUrl: String?,
    val metrica: Double?,
    val fecha: String?,
    val tags: List<String>,
    val descripcion: String?,
    val precio: String?,
    val moneda: String?,
    val pais: String?,
    val urlPreview: String?
)
