package com.jetbrains.kmpapp.util

import java.util.UUID


actual fun epochMillis(): Long = System.currentTimeMillis()

actual class Reloj actual constructor() {
    actual fun ahora(): Long = System.currentTimeMillis()
}