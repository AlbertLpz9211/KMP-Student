package com.jetbrains.kmpapp

import android.os.Build

actual fun plataforma(): String = "Android ${Build.VERSION.SDK_INT}"
