package com.jetbrains.kmpapp.data.repository

import com.jetbrains.kmpapp.data.local.ItemLocalDataSource
import com.jetbrains.kmpapp.data.remote.OpenLibraryApi
import com.jetbrains.kmpapp.data.remote.mapper.toDomain
import com.jetbrains.kmpapp.domain.model.BookStatus
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OpenLibraryRepositoryImpl(
    private val api: OpenLibraryApi,
    private val localDataSource: ItemLocalDataSource,
) : OpenLibraryRepository {

    override fun search(query: String): Flow<Result<List<Item>>> = channelFlow {
        // Emit local data reactively
        launch {
            localDataSource.getItems().collect {
                send(Result.success(it))
            }
        }

        // Fetch from network and update local DB
        val networkResult = api.searchBooks(query)
        networkResult.fold(
            onSuccess = { dto ->
                val items = dto.docs.map { it.toDomain() }
                localDataSource.upsertItems(items)
            },
            onFailure = {
                send(Result.failure(it))
            },
        )
    }

    override fun getDetail(id: String): Flow<Result<ItemDetalle>> = channelFlow {
        // Emit local detail reactively
        launch {
            localDataSource.getDetail(id).collect { detail ->
                detail?.let { send(Result.success(it)) }
            }
        }

        // Fetch from network if needed (empty)
        val currentDetail = localDataSource.getDetail(id).first()
        if (currentDetail == null) {
            val networkResult = api.getWorkDetail(id)
            networkResult.fold(
                onSuccess = { dto ->
                    val baseItem = localDataSource.getItems().first().find { it.id == id }
                    baseItem?.let {
                        val detail = dto.toDomain(it)
                        localDataSource.upsertDetail(detail)
                    }
                },
                onFailure = {
                    send(Result.failure(it))
                },
            )
        }
    }

    override suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        localDataSource.toggleFavorite(id, isFavorite)
    }

    override suspend fun updateBookStatus(id: String, status: BookStatus) {
        localDataSource.updateBookStatus(id, status)
    }

    override fun getFavorites(): Flow<List<Item>> {
        return localDataSource.getFavoriteItems()
    }

    override fun getMyBooks(): Flow<List<Item>> {
        return localDataSource.getMyBooks()
    }
}
