package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.local.DriverFactory
import com.jetbrains.kmpapp.db.CineDb
import org.koin.dsl.module

/**
 * Arranque de Koin en iOS. Se llama desde Swift: `KoinIosKt.doInitKoinIos()` (ver iOSApp.swift).
 */
fun doInitKoinIos() = initKoin(
    platformModule = module {
        single { CineDb(DriverFactory().createDriver()) }
    },
)
