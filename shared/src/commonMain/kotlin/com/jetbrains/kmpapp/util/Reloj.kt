package com.jetbrains.kmpapp.util

expect fun epochMillis(): Long

class Reloj {
    fun ahora(): Long = epochMillis()
}

