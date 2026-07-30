package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.local.DriverFactory
import com.jetbrains.kmpapp.db.CineDb
import com.jetbrains.kmpapp.presentation.list.MovieListViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.module

/**
 * Arranque de Koin en iOS. Se llama desde Swift: `KoinIosKt.doInitKoinIos()` (ver iOSApp.swift).
 * El módulo de plataforma aporta el CineDb con el driver nativo de iOS (no necesita Context).
 */
fun initKoinIos() = initKoin(
    platformModule = module {
        single { CineDb(DriverFactory().createDriver()) }
    },
)

// Swift usa esta clase para obtener el ViewModel registrado en Koin.
class IosViewModelFactory : KoinComponent {
    fun movieListViewModel(): MovieListViewModel = get()
}
