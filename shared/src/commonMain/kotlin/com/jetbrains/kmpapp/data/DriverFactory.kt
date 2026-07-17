package com.jetbrains.kmpapp.data

// src/commonMain/kotlin/com/jetbrains/kmpapp/data/DriverFactory.kt
expect class DriverFactory {
    fun createDriver(): SqlDriver
}