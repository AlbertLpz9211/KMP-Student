package com.jetbrains.kmpapp.domain

data class Movie(
    val id: Int,
    val titulo: String,
    val overview: String,
    val rating: Double,
    val posterUrl: String?,
    val anio: String
)
