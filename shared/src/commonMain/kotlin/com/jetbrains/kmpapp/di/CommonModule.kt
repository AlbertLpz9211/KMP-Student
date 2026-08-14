package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.local.ItemLocalDataSource
import com.jetbrains.kmpapp.data.remote.OpenLibraryApi
import com.jetbrains.kmpapp.data.repository.OpenLibraryRepositoryImpl
import com.jetbrains.kmpapp.db.OpenLibraryDatabase
import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository
import com.jetbrains.kmpapp.domain.usecase.GetFavoritesUseCase
import com.jetbrains.kmpapp.domain.usecase.GetItemDetailUseCase
import com.jetbrains.kmpapp.domain.usecase.GetMyBooksUseCase
import com.jetbrains.kmpapp.domain.usecase.SearchItemsUseCase
import com.jetbrains.kmpapp.domain.usecase.ToggleFavoriteUseCase
import com.jetbrains.kmpapp.domain.usecase.UpdateBookStatusUseCase
import com.jetbrains.kmpapp.screens.detail.DetailViewModel
import com.jetbrains.kmpapp.screens.list.ListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

import com.jetbrains.kmpapp.ThemeViewModel

val commonModule = module {
    single { OpenLibraryApi.createDefaultClient() }
    single { OpenLibraryApi(get()) }
    
    // Database and Local DataSource
    // SqlDriver will be provided by platformModule
    single { OpenLibraryDatabase(get()) }
    single { ItemLocalDataSource(get()) }

    // Repository
    single<OpenLibraryRepository> { OpenLibraryRepositoryImpl(get(), get()) }

    // Use Cases
    factory { SearchItemsUseCase(get()) }
    factory { GetItemDetailUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { GetMyBooksUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
    factory { UpdateBookStatusUseCase(get()) }

    // ViewModels
    viewModelOf(::ListViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::ThemeViewModel)
}
