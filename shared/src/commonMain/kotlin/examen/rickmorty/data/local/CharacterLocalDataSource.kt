package examen.rickmorty.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import examen.rickmorty.domain.Personaje
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
                        ubicacion = entity.locationName,
                        esFavorito = entity.isFavorite == 1L
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
                    locationName = personaje.ubicacion
                )
            }
        }
    }

    fun marcarFavorito(id: Int, esFavorito: Boolean) {
        queries.setFavorite(
            isFavorite = if (esFavorito) 1L else 0L,
            id = id.toLong()
        )
    }
}