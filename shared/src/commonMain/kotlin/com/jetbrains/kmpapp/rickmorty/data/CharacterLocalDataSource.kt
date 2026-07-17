package com.jetbrains.kmpapp.rickmorty.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jetbrains.kmpapp.db.RickMortyDatabase
import com.jetbrains.kmpapp.rickmorty.domain.Personaje
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
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    fun guardarTodos(personajes: List<Personaje>) {
        queries.transaction {
            personajes.forEach { p ->
                queries.insertCharacter(
                    id = p.id.toLong(),
                    name = p.nombre,
                    status = p.estado,
                    species = p.especie,
                    image = p.imagen,
                    locationName = p.ubicacion
                )
            }
        }
    }

    fun marcarFavorito(id: Int, isFavorite: Boolean) {
        queries.setFavorite(if (isFavorite) 1L else 0L, id.toLong())
    }
}

fun com.jetbrains.kmpapp.db.CharacterEntity.toDomain(): Personaje {
    return Personaje(
        id = id.toInt(),
        nombre = name,
        estado = status,
        especie = species,
        imagen = image,
        ubicacion = locationName
    )
}
