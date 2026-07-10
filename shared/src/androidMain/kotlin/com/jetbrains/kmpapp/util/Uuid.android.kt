package com.jetbrains.kmpapp.util

actual fun nuevoUuid(): String = java.util.UUID.randomUUID().toString()
