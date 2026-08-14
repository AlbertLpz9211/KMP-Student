package com.jetbrains.kmpapp.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.domain.model.BookStatus
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import com.jetbrains.kmpapp.domain.usecase.GetItemDetailUseCase
import com.jetbrains.kmpapp.domain.usecase.ToggleFavoriteUseCase
import com.jetbrains.kmpapp.domain.usecase.UpdateBookStatusUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getItemDetailUseCase: GetItemDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val updateBookStatusUseCase: UpdateBookStatusUseCase,
) : ViewModel() {
    fun getObject(objectId: String): Flow<ItemDetalle?> = 
        getItemDetailUseCase(objectId).map { it.getOrNull() }

    fun toggleFavorite(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(id, isFavorite)
        }
    }

    fun updateStatus(id: String, status: BookStatus) {
        viewModelScope.launch {
            updateBookStatusUseCase(id, status)
        }
    }
}
