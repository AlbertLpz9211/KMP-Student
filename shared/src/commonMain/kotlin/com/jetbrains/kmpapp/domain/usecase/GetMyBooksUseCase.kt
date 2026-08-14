package com.jetbrains.kmpapp.domain.usecase

import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository
import kotlinx.coroutines.flow.Flow

class GetMyBooksUseCase(private val repository: OpenLibraryRepository) {
    operator fun invoke(): Flow<List<Item>> = repository.getMyBooks()
}
