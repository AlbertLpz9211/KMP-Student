package com.jetbrains.kmpapp.domain.usecase

import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllItemsUseCase(private val repository: OpenLibraryRepository) {
    operator fun invoke(): Flow<List<Item>> {
        return repository.getAllItems().map { items ->
            items.sortedBy { it.titulo }
        }
    }
}
