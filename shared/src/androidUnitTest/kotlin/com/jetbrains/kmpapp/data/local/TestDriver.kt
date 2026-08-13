package com.jetbrains.kmpapp.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.jetbrains.kmpapp.db.OpenLibraryDatabase

actual fun createTestDriver(): SqlDriver {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    OpenLibraryDatabase.Schema.create(driver)
    return driver
}
