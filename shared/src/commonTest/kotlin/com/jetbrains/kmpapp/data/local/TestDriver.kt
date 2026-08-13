package com.jetbrains.kmpapp.data.local

import app.cash.sqldelight.db.SqlDriver

expect fun createTestDriver(): SqlDriver
