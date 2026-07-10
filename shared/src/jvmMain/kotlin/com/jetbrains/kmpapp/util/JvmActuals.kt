package com.jetbrains.kmpapp.util

actual fun nuevoUuid(): String = java.util.UUID.randomUUID().toString()

actual fun infoDispositivo(): String = "JVM Desktop"

actual fun epochMillis(): Long = System.currentTimeMillis()
