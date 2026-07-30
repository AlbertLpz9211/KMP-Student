package com.jetbrains.kmpapp.rickmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.rickmorty.data.PersonajeRepository
import com.jetbrains.kmpapp.rickmorty.domain.Personaje
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonajeViewModel(
    private val repository: PersonajeRepository
) : ViewModel() {

    val personajes: StateFlow<List<Personaje>> = repository.observar()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refrescar()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
