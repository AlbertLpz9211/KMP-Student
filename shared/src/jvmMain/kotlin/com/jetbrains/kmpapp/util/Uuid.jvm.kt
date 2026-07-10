package com.jetbrains.kmpapp.util

import java.util.UUID

actual fun nuevoUuid(): String = UUID.randomUUID().toString()

actual fun epochMillis(): Long = System.currentTimeMillis()

actual class Reloj {
    actual fun ahora(): Long = System.currentTimeMillis()
}
