package com.jetbrains.kmpapp.data.network

Package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.network.MovieDTO
import com.jetbrains.kmpapp.domain.Movie

fun MovieDTO.toDomain(): Movie {
    val year = releaseDate?.take(4) ?: "N/A"

    val fullPosterUrl = if (posterPath != null) {
        "https://image.tmdb.org/t/p/w500$posterPath"
    } else {
        ""
    }

    return Movie(
        id = id,
        title = title,
        overview = overview,
        voteAverage = voteAverage ?: 0.0,
        posterUrl = fullPosterUrl,
        releaseYear = year
    )
}
