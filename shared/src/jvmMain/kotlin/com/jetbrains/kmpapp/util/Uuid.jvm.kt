package com.jetbrains.kmpapp.util

import java.util.UUID

actual fun nuevoUuid(): String = UUID.randomUUID().toString()
