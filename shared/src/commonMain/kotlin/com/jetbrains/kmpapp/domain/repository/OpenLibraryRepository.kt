package com.jetbrains.kmpapp.domain.repository

import com.jetbrains.kmpapp.domain.model.BookStatus
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import kotlinx.coroutines.flow.Flow

interface OpenLibraryRepository {
    fun search(query: String): Flow<Result<List<Item>>>
    fun getDetail(id: String): Flow<Result<ItemDetalle>>
    fun getFavorites(): Flow<List<Item>>
    fun getMyBooks(): Flow<List<Item>>
    fun getAllItems(): Flow<List<Item>>
    suspend fun toggleFavorite(id: String, isFavorite: Boolean)
    suspend fun updateBookStatus(id: String, status: BookStatus)
}
