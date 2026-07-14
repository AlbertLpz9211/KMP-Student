package com.jetbrains.kmpapp.data

// Este objeto estático sirve como almacén central para las configuraciones de la API.
// Lo usamos para que el resto del código pueda acceder a la llave de forma fácil.
object TmdbConfig {
    // Definimos una constante con tu llave de acceso personal.
    // IMPORTANTE: Este archivo no se subirá a Git gracias al archivo .gitignore.
    const val TMDB_API_KEY = "f254f1b44f6dac52075689f3a10b2245"
}
