package com.jetbrains.kmpapp.screens.detail

import androidx.lifecycle.ViewModel
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import com.jetbrains.kmpapp.domain.usecase.GetItemDetailUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetailViewModel(private val getItemDetailUseCase: GetItemDetailUseCase) : ViewModel() {
    fun getObject(objectId: String): Flow<ItemDetalle?> = 
        getItemDetailUseCase(objectId).map { it.getOrNull() }
}
