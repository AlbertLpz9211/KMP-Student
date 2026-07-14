package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.MovieDto
import com.jetbrains.kmpapp.domain.Movie

fun MovieDto.toDomain(): Movie {
    val posterBaseUrl = "https://image.tmdb.org/t/p/w500"
    return Movie(
        id = id,
        title = title,
        overview = overview,
        rating = voteAverage,
        posterUrl = posterPath?.let { "$posterBaseUrl$it" } ?: "",
        releaseYear = releaseDate?.take(4) ?: "N/A"
    )
}
