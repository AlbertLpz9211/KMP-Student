package com.jetbrains.kmpapp.domain

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    val posterUrl: String,
    val releaseYear: String
)