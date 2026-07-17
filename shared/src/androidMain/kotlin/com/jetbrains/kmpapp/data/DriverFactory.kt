package com.jetbrains.kmpapp.data

import android.content.Context

// src/androidMain/kotlin/com/jetbrains/kmpapp/data/DriverFactory.kt
actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "rickmorty.db")
    }
}