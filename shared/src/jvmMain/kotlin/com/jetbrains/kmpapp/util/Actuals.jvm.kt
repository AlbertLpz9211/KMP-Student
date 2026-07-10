package com.jetbrains.kmpapp.util

actual fun epochMillis(): Long = System.currentTimeMillis()

actual class Reloj actual constructor() {
    actual fun ahora(): Long = System.currentTimeMillis()
}