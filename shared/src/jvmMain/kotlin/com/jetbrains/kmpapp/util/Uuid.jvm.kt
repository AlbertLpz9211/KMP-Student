package com.jetbrains.kmpapp.util

actual fun nuevoUuid(): String {
    return java.util.UUID.randomUUID().toString()
}