package com.jetbrains.kmpapp.domain.usecase

import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository

class ToggleFavoriteUseCase(private val repository: OpenLibraryRepository) {
    suspend operator fun invoke(id: String, isFavorite: Boolean) {
        repository.toggleFavorite(id, isFavorite)
    }
}
