package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.local.DriverFactory
import org.koin.dsl.module

actual val platformModule = module {
    single { DriverFactory() }
}
