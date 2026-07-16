package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.MovieDto
import com.jetbrains.kmpapp.domain.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        titulo = title,
        overview = overview,
        rating = voteAverage,
        posterUrl = posterPath?.let {
            "https://image.tmdb.org/t/p/w500$it"
        },
        anio = releaseDate?.take(4) ?: ""
    )
}

