package com.jetbrains.kmpapp.domain.usecase

import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchItemsUseCase(private val repository: OpenLibraryRepository) {
    operator fun invoke(query: String): Flow<Result<List<Item>>> {
        if (query.isBlank()) return flowOf(Result.success(emptyList()))
        return repository.search(query)
    }
}
