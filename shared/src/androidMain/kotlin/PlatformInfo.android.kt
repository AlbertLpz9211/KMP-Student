package com.jetbrains.kmpapp
import android.os.Build

actual fun infoDispositivo(): String {
    return Build.MODEL
}