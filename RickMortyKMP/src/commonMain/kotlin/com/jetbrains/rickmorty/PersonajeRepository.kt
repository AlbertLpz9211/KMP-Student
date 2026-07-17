package com.jetbrains.rickmorty

import com.jetbrains.rickmorty.api.RickMortyApi
import com.jetbrains.rickmorty.db.CharacterLocalDataSource
import com.jetbrains.rickmorty.domain.Personaje
import com.jetbrains.rickmorty.domain.toDomain
import kotlinx.coroutines.flow.Flow

class PersonajeRepository(
    private val api: RickMortyApi,
    private val localDataSource: CharacterLocalDataSource
) {
    fun observar(): Flow<List<Personaje>> {
        return localDataSource.observarTodos()
    }

    suspend fun refrescar() {
        val response = api.personajes(1)
        val personajes = response.results.map { it.toDomain() }
        localDataSource.guardarTodos(personajes)
    }
}
