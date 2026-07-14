package com.jetbrains.kmpapp.domain

// Esta es la clase de Dominio. Representa una película con un formato limpio y listo 
// para ser mostrado en la interfaz de usuario de nuestra aplicación.
data class Movie(
    val id: Int, // Identificador único de la película.
    val titulo: String, // El título que el usuario verá en pantalla.
    val overview: String, // El resumen o descripción de la película.
    val rating: Double, // La calificación numérica (ejemplo: 7.5).
    val posterUrl: String, // La dirección de internet completa para descargar la imagen.
    val anio: String // El año de estreno (ejemplo: "2024").
)
