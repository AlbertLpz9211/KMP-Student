package com.jetbrains.kmpapp.rickmorty.data

import com.jetbrains.kmpapp.rickmorty.domain.Personaje
import kotlinx.coroutines.flow.Flow

class PersonajeRepository(
    private val api: RickMortyApi,
    private val local: CharacterLocalDataSource
) {
    fun observar(): Flow<List<Personaje>> {
        return local.observarTodos()
    }

    suspend fun refrescar() {
        val page = api.personajes(1)
        val personajes = page.results.map { it.toDomain() }
        local.guardarTodos(personajes)
    }
}
