package com.jetbrains.kmpapp.data

// src/iosMain/kotlin/com/jetbrains/kmpapp/data/DriverFactory.kt
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "rickmorty.db")
    }
}