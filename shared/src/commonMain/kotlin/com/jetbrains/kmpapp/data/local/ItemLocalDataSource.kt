package com.jetbrains.kmpapp.data.local

import com.jetbrains.kmpapp.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemLocalDataSource {
    fun getAllItems(): Flow<List<Item>>
    suspend fun insertItems(items: List<Item>)
    suspend fun getUltimaActualizacion(query: String): Long?
    suspend fun marcarActualizacion(query: String, timestamp: Long)
    suspend fun setFavorito(id: String, isFavorito: Boolean)
    suspend fun isFavorito(id: String): Boolean
}
