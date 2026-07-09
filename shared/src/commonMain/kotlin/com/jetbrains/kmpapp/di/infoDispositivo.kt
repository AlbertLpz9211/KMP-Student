package com.jetbrains.kmpapp.di

import platform.UIKit.UIDevice

actual fun infoDispositivo(): String {
    return UIDevice.currentDevice.model
}