package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.RickMortyApi
import com.jetbrains.kmpapp.data.PersonajeRepository
import com.jetbrains.kmpapp.db.CharacterLocalDataSource
import com.jetbrains.kmpapp.db.DriverFactory
import com.jetbrains.kmpapp.db.RickMortyDatabase
import com.jetbrains.kmpapp.screens.detail.DetailViewModel
import com.jetbrains.kmpapp.screens.list.ListViewModel
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        Json { ignoreUnknownKeys = true }
    }
    single { RickMortyApi() }
    
    single { RickMortyDatabase(get<DriverFactory>().createDriver()) }
    single { CharacterLocalDataSource(get()) }
    single { PersonajeRepository(get(), get()) }
}

val viewModelModule = module {
    factoryOf(::ListViewModel)
    factoryOf(::DetailViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}
