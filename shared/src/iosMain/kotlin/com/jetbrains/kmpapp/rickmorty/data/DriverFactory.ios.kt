package com.jetbrains.kmpapp.rickmorty.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jetbrains.kmpapp.db.RickMortyDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RickMortyDatabase.Schema, "rickmorty.db")
    }
}
