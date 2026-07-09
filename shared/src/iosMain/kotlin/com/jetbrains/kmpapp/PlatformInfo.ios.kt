package com.jetbrains.kmpapp
import platform.UIKit.UIDevice

actual fun infoDispositivo(): String {
    return UIDevice.currentDevice.model
}