package com.jetbrains.kmpapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviePageDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int
)

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("release_date") val releaseDate: String? = null
)

@Serializable
data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    val genres: List<GenreDto> = emptyList(),
    val runtime: Int? = null,
    val budget: Long? = null,
    val revenue: Long? = null
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)
