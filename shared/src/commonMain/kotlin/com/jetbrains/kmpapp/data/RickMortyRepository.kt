package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.CharacterDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RickMortyRepository(private val api: RickMortyApi) {

    // Función simple para obtener personajes de una página
    suspend fun fetchCharacters(page: Int): List<CharacterDto> {
        return try {
            val response = api.getCharacters(page)
            response.results // Retornamos solo la lista de personajes
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Opcional: Usando Flow para emitir la lista (útil para estados asíncronos)
    fun getCharactersFlow(page: Int): Flow<List<CharacterDto>> = flow {
        val data = api.getCharacters(page)
        emit(data.results)
    }
}