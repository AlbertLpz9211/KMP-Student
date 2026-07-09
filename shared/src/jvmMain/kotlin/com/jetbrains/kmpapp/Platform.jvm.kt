package com.jetbrains.kmpapp

actual fun plataforma(): String = "JVM ${System.getProperty("java.version")}"

actual fun infoDispositivo(): String = System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")"
