package com.jetbrains.kmpapp.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jetbrains.kmpapp.db.OpenLibraryDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(OpenLibraryDatabase.Schema, "openlibrary.db")
    }
}
