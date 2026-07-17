package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.db.CharacterLocalDataSource
import com.jetbrains.kmpapp.domain.Personaje
import kotlinx.coroutines.flow.Flow

class PersonajeRepository(
    private val api: RickMortyApi,
    private val localDataSource: CharacterLocalDataSource
) {
    fun observar(): Flow<List<Personaje>> {
        return localDataSource.observarTodos()
    }

    suspend fun refrescar(pagina: Int = 1) {
        val response = api.personajes(pagina)
        val personajes = response.results.map { it.toDomain() }
        localDataSource.guardarTodos(personajes)
    }

    suspend fun marcarFavorito(id: Int, isFavorite: Boolean) {
        localDataSource.marcarFavorito(id, isFavorite)
    }
}
