package com.jetbrains.kmpapp.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.usecase.GetAllItemsUseCase
import com.jetbrains.kmpapp.domain.usecase.GetFavoritesUseCase
import com.jetbrains.kmpapp.domain.usecase.GetMyBooksUseCase
import com.jetbrains.kmpapp.domain.usecase.SearchItemsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModel(
    searchItemsUseCase: SearchItemsUseCase,
    getAllItemsUseCase: GetAllItemsUseCase,
    getFavoritesUseCase: GetFavoritesUseCase,
    getMyBooksUseCase: GetMyBooksUseCase
) : ViewModel() {
    private val _query = MutableStateFlow("")

    val items: StateFlow<List<Item>> =
        _query.flatMapLatest { q ->
            searchItemsUseCase(q).map { result ->
                result.getOrDefault(emptyList())
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allItems: StateFlow<List<Item>> = getAllItemsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites: StateFlow<List<Item>> = getFavoritesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val myBooks: StateFlow<List<Item>> = getMyBooksUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}
