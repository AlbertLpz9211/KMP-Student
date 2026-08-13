package com.jetbrains.kmpapp.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = MyDatabase.Schema, // Reemplazar con el esquema real generado
            context = context,
            name = "itunes.db"
        )
    }
}
