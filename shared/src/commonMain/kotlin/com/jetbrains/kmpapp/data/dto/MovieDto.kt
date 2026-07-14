package com.jetbrains.kmpapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Definimos la estructura de datos para recibir una página de resultados desde la API.
// Usamos @Serializable para que la librería sepa cómo convertir el JSON a esta clase.
@Serializable
data class MoviePageDto(
    val page: Int, // Guarda el número de la página que estamos consultando.
    val results: List<MovieDto>, // Contiene la lista de películas que vienen en esta respuesta.
    @SerialName("total_pages") val totalPages: Int // En el JSON se llama 'total_pages', aquí lo guardamos en totalPages.
)

// Definimos la estructura de datos para una película individual tal cual viene desde internet.
// Estos objetos se llaman DTO (Data Transfer Objects) porque solo sirven para mover datos de la red a la app.
@Serializable
data class MovieDto(
    val id: Int, // El número único que identifica a la película en el servidor.
    val title: String, // El nombre o título original de la cinta.
    val overview: String, // La descripción o sinopsis de lo que trata la película.
    @SerialName("vote_average") val voteAverage: Double, // La puntuación promedio dada por los usuarios (rating).
    @SerialName("poster_path") val posterPath: String? = null, // La ruta parcial de la imagen del póster.
    @SerialName("release_date") val releaseDate: String? = null // La fecha en la que se estrenó la película.
)
