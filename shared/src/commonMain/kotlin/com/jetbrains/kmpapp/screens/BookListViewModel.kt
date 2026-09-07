package com.jetbrains.kmpapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.BookRepository
import com.jetbrains.kmpapp.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface BookUiState {
    data object Loading : BookUiState

    data class Success(val items: List<Item>) : BookUiState

    data class Error(val message: String) : BookUiState
}

class BookListViewModel(
    private val repository: BookRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<BookUiState>(BookUiState.Loading)
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = BookUiState.Loading
            try {
                println("Cargando libros...")
                val books = repository.getBooks()
                println("Libros cargados: ${books.size}")
                _uiState.value = BookUiState.Success(books)
            } catch (e: Exception) {
                println("Error cargando libros: ${e.message}")
                _uiState.value = BookUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
