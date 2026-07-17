package com.jetbrains.rickmorty.db

import app.cash.sqldelight.db.SqlDriver

expect fun createTestDriver(): SqlDriver
