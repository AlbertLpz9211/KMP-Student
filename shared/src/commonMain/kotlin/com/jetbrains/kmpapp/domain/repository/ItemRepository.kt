package com.jetbrains.kmpapp.domain.repository

import com.jetbrains.kmpapp.domain.model.AppError
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.Resultado
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun observarCatalogo(): Flow<List<Item>>
    suspend fun buscar(query: String): Resultado<Unit>
    suspend fun alternarFavorito(id: String)
}
