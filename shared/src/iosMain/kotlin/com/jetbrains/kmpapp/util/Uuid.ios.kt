package com.jetbrains.kmpapp.util

import platform.Foundation.NSUUID
actual fun nuevoUuid(): String = NSUUID().UUIDString()
