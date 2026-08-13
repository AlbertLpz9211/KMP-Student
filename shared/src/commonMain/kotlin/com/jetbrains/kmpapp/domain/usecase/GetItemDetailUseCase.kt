package com.jetbrains.kmpapp.domain.usecase

import com.jetbrains.kmpapp.domain.model.ItemDetalle
import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository
import kotlinx.coroutines.flow.Flow

class GetItemDetailUseCase(private val repository: OpenLibraryRepository) {
    operator fun invoke(id: String): Flow<Result<ItemDetalle>> {
        return repository.getDetail(id)
    }
}
