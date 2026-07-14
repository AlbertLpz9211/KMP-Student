package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.MovieDto
import com.jetbrains.kmpapp.domain.Movie

// Esta es una función de extensión que actúa como un "traductor".
// Su trabajo es tomar una película en formato de internet (MovieDto) y convertirla al formato de nuestra app (Movie).
fun MovieDto.toDomain(): Movie {
    // Creamos y devolvemos un nuevo objeto Movie con los datos transformados.
    return Movie(
        id = this.id, // Pasamos el ID tal cual viene.
        titulo = this.title, // Pasamos el título directamente.
        overview = this.overview, // Pasamos la sinopsis.
        rating = this.voteAverage, // Pasamos la calificación.
        
        // Transformamos la ruta parcial de la imagen en una URL completa que el teléfono pueda abrir.
        // Si no hay ruta (es nulo), devolvemos un texto vacío.
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
        
        // Transformamos la fecha completa (ej: "2024-12-31") para quedarnos solo con el año (ej: "2024").
        // Usamos '.take(4)' para agarrar las primeras 4 letras/números de la cadena de texto.
        anio = this.releaseDate?.take(4) ?: "N/A"
    )
}
