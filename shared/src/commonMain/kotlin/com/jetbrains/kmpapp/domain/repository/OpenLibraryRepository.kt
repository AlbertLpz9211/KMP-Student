package com.jetbrains.kmpapp.domain.repository

import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import kotlinx.coroutines.flow.Flow

interface OpenLibraryRepository {
    fun search(query: String): Flow<Result<List<Item>>>
    fun getDetail(id: String): Flow<Result<ItemDetalle>>
}
