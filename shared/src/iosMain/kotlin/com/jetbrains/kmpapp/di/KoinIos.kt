package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.local.DriverFactory
import com.jetbrains.kmpapp.db.CineDb
import com.jetbrains.kmpapp.presentation.list.MovieListState
import com.jetbrains.kmpapp.presentation.list.MovieListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module
import androidx.lifecycle.viewModelScope

/**
 * Arranque de Koin en iOS. Se llama desde Swift: `KoinIosKt.doInitKoinIos()` (ver iOSApp.swift).
 * El módulo de plataforma aporta el CineDb con el driver nativo de iOS (no necesita Context).
 */
fun initKoinIos() = initKoin(
    platformModule = module {
        single { CineDb(DriverFactory().createDriver()) }
    },
)

/**
 * Helper para que SwiftUI pueda pedir los ViewModels de Kotlin.
 */
object ViewModelProvider : KoinComponent {
    fun getMovieListViewModel(): MovieListViewModel = get()
}

/**
 * Extensiones para observar flujos desde Swift de forma sencilla.
 */
fun observeMovieListState(viewModel: MovieListViewModel, onChange: (MovieListState) -> Unit) {
    viewModel.state.onEach { onChange(it) }.launchIn(viewModel.viewModelScope)
}
