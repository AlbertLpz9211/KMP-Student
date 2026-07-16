package com.jetbrains.kmpapp.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// --- DTO para listas de películas (Top Rated, Now Playing, Buscar) ---
@Serializable
data class MovieResponseDTO(
    val results: List<MovieDTO>
)

@Serializable
data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null
)

// --- DTO para el Detalle de la película ---
@Serializable
data class MovieDetailDTO(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<GenreDTO>,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    val runtime: Int? = null
)

@Serializable
data class GenreDTO(
    val id: Int,
    val name: String
)