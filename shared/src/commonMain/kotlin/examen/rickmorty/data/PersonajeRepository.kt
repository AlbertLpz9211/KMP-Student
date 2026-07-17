package examen.rickmorty.data

import examen.rickmorty.data.api.RickMortyApi
import examen.rickmorty.data.api.toDomain
import examen.rickmorty.data.local.CharacterLocalDataSource
import examen.rickmorty.domain.Personaje
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio offline-first: la UI observa SIEMPRE de la base de datos (SQLDelight).
 * La red solo se utiliza para alimentar y actualizar la base de datos local.
 * Esto permite que la app funcione sin conexión mostrando los últimos datos cacheados.
 */
class PersonajeRepository(
    private val api: RickMortyApi,
    private val local: CharacterLocalDataSource
) {
    // (a) Método observar(): lee SIEMPRE de la base de datos
    fun observar(): Flow<List<Personaje>> = local.observarTodos()

    // (b) Método refrescar(): baja de la API y guarda en la DB
    suspend fun refrescar() {
        try {
            val response = api.personajes(pagina = 1)
            val personajes = response.results.map { it.toDomain() }
            local.guardarTodos(personajes)
        } catch (e: Exception) {
            // En una app real, aquí manejaríamos el error (log, relanzar, etc.)
            e.printStackTrace()
        }
    }
    
    fun marcarFavorito(id: Int, esFavorito: Boolean) {
        local.marcarFavorito(id, esFavorito)
    }
}