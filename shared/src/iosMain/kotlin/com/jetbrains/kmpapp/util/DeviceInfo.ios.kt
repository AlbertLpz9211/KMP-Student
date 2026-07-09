package com.jetbrains.kmpapp.util

import platform.UIKit.UIDevice

actual fun infoDispositivo(): String = UIDevice.currentDevice.name
