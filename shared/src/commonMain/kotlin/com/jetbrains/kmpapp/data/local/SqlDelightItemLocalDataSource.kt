package com.jetbrains.kmpapp.data.local

import com.jetbrains.kmpapp.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SqlDelightItemLocalDataSource(
    // private val database: MyDatabase 
) : ItemLocalDataSource {
    override fun getAllItems(): Flow<List<Item>> = flowOf(emptyList())

    override suspend fun insertItems(items: List<Item>) {
        // TODO: Implementar con SQLDelight queries
    }

    override suspend fun getUltimaActualizacion(query: String): Long? = null

    override suspend fun marcarActualizacion(query: String, timestamp: Long) {
        // TODO: Implementar con SQLDelight queries
    }

    override suspend fun setFavorito(id: String, isFavorito: Boolean) {
        // TODO: Implementar con SQLDelight queries
    }

    override suspend fun isFavorito(id: String): Boolean = false
}
