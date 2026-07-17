package com.jetbrains.kmpapp.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jetbrains.kmpapp.domain.Personaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterLocalDataSource(database: RickMortyDatabase) {
    private val queries = database.characterQueries

    fun observarTodos(): Flow<List<Personaje>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    Personaje(
                        id = entity.id.toInt(),
                        nombre = entity.name,
                        estado = entity.status,
                        especie = entity.species,
                        imagen = entity.image,
                        ubicacion = entity.locationName
                    )
                }
            }
    }

    suspend fun guardarTodos(personajes: List<Personaje>) {
        queries.transaction {
            personajes.forEach { personaje ->
                queries.insertCharacter(
                    id = personaje.id.toLong(),
                    name = personaje.nombre,
                    status = personaje.estado,
                    species = personaje.especie,
                    image = personaje.imagen,
                    locationName = personaje.ubicacion,
                    isFavorite = 0 // Default to 0, or we could preserve if existing
                )
            }
        }
    }

    suspend fun marcarFavorito(id: Int, isFavorite: Boolean) {
        queries.setFavorite(
            isFavorite = if (isFavorite) 1L else 0L,
            id = id.toLong()
        )
    }
}
