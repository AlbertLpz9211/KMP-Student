package com.jetbrains.rickmorty.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual fun createTestDriver(): SqlDriver {
    return NativeSqliteDriver(RickMortyDatabase.Schema, "test.db")
}
