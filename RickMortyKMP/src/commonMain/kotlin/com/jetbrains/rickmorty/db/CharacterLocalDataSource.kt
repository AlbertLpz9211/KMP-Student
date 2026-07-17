package com.jetbrains.rickmorty.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jetbrains.rickmorty.domain.Personaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterLocalDataSource(database: RickMortyDatabase) {
    private val queries = database.characterQueries

    fun observarTodos(): Flow<List<Personaje>> {
        return queries.selectAll().asFlow().mapToList(Dispatchers.IO).map { list ->
            list.map { 
                Personaje(
                    id = it.id.toInt(), 
                    nombre = it.name, 
                    estado = it.status, 
                    especie = it.species, 
                    imagen = it.image, 
                    ubicacion = it.locationName
                )
            }
        }
    }

    suspend fun guardarTodos(personajes: List<Personaje>) {
        queries.transaction {
            personajes.forEach { 
                queries.insertCharacter(
                    id = it.id.toLong(),
                    name = it.nombre,
                    status = it.estado,
                    species = it.especie,
                    image = it.imagen,
                    locationName = it.ubicacion
                )
            }
        }
    }

    suspend fun marcarFavorito(id: Int, isFavorite: Boolean) {
        queries.setFavorite(if (isFavorite) 1L else 0L, id.toLong())
    }
}
