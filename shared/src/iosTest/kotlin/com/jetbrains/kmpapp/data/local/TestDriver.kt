package com.jetbrains.kmpapp.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jetbrains.kmpapp.db.OpenLibraryDatabase

actual fun createTestDriver(): SqlDriver {
    return NativeSqliteDriver(OpenLibraryDatabase.Schema, "test.db")
}
