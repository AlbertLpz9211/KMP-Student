package com.jetbrains.kmpapp.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MuseumRepository(
    private val museumApi: MuseumApi,
    private val museumStorage: MuseumStorage,
    private val tmdbApi: TmdbApi, // Añadimos nuestra nueva API de pelis
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        // Obtenemos las pelis populares de internet
        val peliPage = tmdbApi.populares()
        
        // Las convertimos al formato que la App ya entiende (MuseumObject)
        val pelisMapeadas = peliPage.results.map { dto ->
            MuseumObject(
                objectID = dto.id,
                title = dto.title,
                artistDisplayName = "Calificación: ${dto.voteAverage}",
                medium = dto.overview,
                dimensions = "",
                objectURL = "",
                objectDate = dto.releaseDate ?: "",
                primaryImage = dto.posterPath?.let { "https://image.tmdb.org/t/p/w780$it" } ?: "",
                primaryImageSmall = dto.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" } ?: "",
                repository = "TMDB",
                department = "",
                creditLine = ""
            )
        }
        
        // Guardamos las pelis en el almacenamiento local
        museumStorage.saveObjects(pelisMapeadas)
    }

    fun getObjects(): Flow<List<MuseumObject>> = museumStorage.getObjects()

    fun getObjectById(objectId: Int): Flow<MuseumObject?> = museumStorage.getObjectById(objectId)
}
