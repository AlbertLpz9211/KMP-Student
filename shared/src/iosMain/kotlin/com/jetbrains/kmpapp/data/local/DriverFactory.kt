package com.jetbrains.kmpapp.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jetbrains.kmpapp.db.OpenLibraryDatabase
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(OpenLibraryDatabase.Schema, "openlibrary.db")
    }
}

actual fun getCurrentMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()
