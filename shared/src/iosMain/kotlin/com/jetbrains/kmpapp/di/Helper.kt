package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.screens.list.ListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import androidx.lifecycle.viewModelScope

object ViewModelProvider : KoinComponent {
    fun getListViewModel(): ListViewModel = get()
}

fun observeListItems(viewModel: ListViewModel, onChange: (List<Item>) -> Unit) {
    viewModel.items.onEach { onChange(it) }.launchIn(viewModel.viewModelScope)
}
