package com.jetbrains.kmpapp.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jetbrains.kmpapp.domain.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SqlDelightItemLocalDataSource(
    private val database: MyDatabase
) : ItemLocalDataSource {
    private val queries = database.appDatabaseQueries

    override fun getAllItems(): Flow<List<Item>> {
        return queries.seleccionarTodosLosFavoritos()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    Item(
                        id = entity.id,
                        titulo = entity.titulo,
                        subtitulo = entity.subtitulo ?: "",
                        imagenUrl = entity.imagenUrl ?: "",
                        metrica = entity.metrica ?: 0.0,
                        fecha = entity.fecha ?: "",
                        tags = entity.tags?.split(",")?.filter { it.isNotBlank() } ?: emptyList()
                    )
                }
            }
    }

    override suspend fun insertItems(items: List<Item>) {
        database.transaction {
            items.forEach { item ->
                queries.insertarFavorito(
                    id = item.id,
                    titulo = item.titulo,
                    subtitulo = item.subtitulo,
                    imagenUrl = item.imagenUrl,
                    metrica = item.metrica,
                    fecha = item.fecha,
                    tags = item.tags.joinToString(",")
                )
            }
        }
    }

    override suspend fun getUltimaActualizacion(query: String): Long? {
        return queries.obtenerUltimaActualizacion(query).executeAsOneOrNull()
    }

    override suspend fun marcarActualizacion(query: String, timestamp: Long) {
        queries.marcarActualizacion(query, timestamp)
    }

    override suspend fun setFavorito(id: String, isFavorito: Boolean) {
        // En este esquema, 'FavoritoEntity' se usa para el catálogo general.
        // Si no existe, no hacemos nada o podríamos borrarlo si isFavorito es false.
        // Pero basándome en los nombres de las queries (.sq), parece que FavoritoEntity es la tabla de Items.
        if (!isFavorito) {
            queries.eliminarFavorito(id)
        }
    }

    override suspend fun isFavorito(id: String): Boolean {
        return queries.seleccionarFavoritoPorId(id).executeAsOneOrNull() != null
    }
}
