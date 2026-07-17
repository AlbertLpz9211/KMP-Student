package com.jetbrains.kmpapp.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jetbrains.kmpapp.CharacterEntity
import com.jetbrains.kmpapp.data.dto.CharacterDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CharacterLocalDataSource(database: AppDatabase) {
    // Obtenemos las queries generadas por SQLDelight
    private val queries = database.characterQueries

    /**
     * (c) observarTodos: Devuelve un Flow que se actualiza
     * automáticamente cada vez que la base de datos cambia.
     */
    fun observarTodos(): Flow<List<CharacterEntity>> {
        return queries
            .selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    /**
     * (c) guardarTodos: Inserta una lista de personajes de la API.
     * Usamos una 'transaction' para que sea mucho más rápido y atómico.
     */
    suspend fun guardarTodos(personajes: List<CharacterDto>) = withContext(Dispatchers.IO) {
        queries.transaction {
            personajes.forEach { dto ->
                queries.insertCharacter(
                    id = dto.id.toLong(),
                    name = dto.name,
                    status = dto.status,
                    species = dto.species,
                    image = dto.image,
                    locationName = "Desconocida", // O dto.location.name si existe
                    isFavorite = 0 // Por defecto no es favorito al descargar
                )
            }
        }
    }

    /**
     * (c) marcarFavorito: Actualiza el estado de favorito.
     * SQLDelight usa Long para INTEGER (1 = true, 0 = false).
     */
    suspend fun marcarFavorito(id: Long, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        val favoriteValue = if (isFavorite) 1L else 0L
        queries.setFavorite(
            isFavorite = favoriteValue,
            id = id
        )
    }
}