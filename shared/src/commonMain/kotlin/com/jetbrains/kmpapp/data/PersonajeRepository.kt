package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.CharacterEntity
import kotlinx.coroutines.flow.Flow

/**
 * Concepto clave: La UI observa la DB; la red solo alimenta la DB.
 * Esto garantiza que la app siempre muestre datos (aunque sean antiguos)
 * incluso si no hay conexión a internet en ese momento.
 */
class PersonajeRepository(
    private val api: RickMortyApi,
    private val localDataSource: CharacterLocalDataSource
) {

    // (a) Leer SIEMPRE de la base de datos
    fun observar(): Flow<List<CharacterEntity>> {
        return localDataSource.observarTodos()
    }

    // (b) Bajar de la red y guardar en la DB
    suspend fun refrescar() {
        try {
            // Bajamos los personajes (asumimos página 1 para este ejercicio)
            val respuesta = api.getCharacters(page = 1)

            // Los persistimos en la base de datos local
            localDataSource.guardarTodos(respuesta.results)
        } catch (e: Exception) {
            // Podríamos loguear el error, pero observar() seguirá
            // emitiendo lo que ya había en la DB.
            println("Error al refrescar: ${e.message}")
        }
    }
}