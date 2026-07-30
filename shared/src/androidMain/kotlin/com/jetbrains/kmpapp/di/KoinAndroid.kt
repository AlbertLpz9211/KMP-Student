package com.jetbrains.kmpapp.di

import android.content.Context
import com.jetbrains.kmpapp.data.local.DriverFactory
import com.jetbrains.kmpapp.db.CineDb
import com.jetbrains.kmpapp.rickmorty.db.RickMortyDatabase
import com.jetbrains.kmpapp.rickmorty.data.DriverFactory as RickMortyDriverFactory
import org.koin.dsl.module

/**
 * Arranque de Koin en ANDROID. Se llama desde CineApp.onCreate(this).
 * El módulo de plataforma aporta el CineDb creado con el driver de Android (necesita Context).
 */
fun initKoinAndroid(context: Context) = initKoin(
    platformModule = module {
        single { CineDb(DriverFactory(context).createDriver()) }
        single { RickMortyDatabase(RickMortyDriverFactory(context).createDriver()) }
    },
)
