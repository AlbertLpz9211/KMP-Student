package com.jetbrains.kmpapp.domain.usecase

import com.jetbrains.kmpapp.domain.model.BookStatus
import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository

class UpdateBookStatusUseCase(private val repository: OpenLibraryRepository) {
    suspend operator fun invoke(id: String, status: BookStatus) {
        repository.updateBookStatus(id, status)
    }
}
