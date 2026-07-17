package com.jetbrains.rickmorty.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        // En una app real, el contexto se pasaría al constructor o se inyectaría.
        // Aquí usamos un placeholder o asumimos que el entorno de test/app proveerá lo necesario.
        throw UnsupportedOperationException("Context required for Android driver")
    }
}
