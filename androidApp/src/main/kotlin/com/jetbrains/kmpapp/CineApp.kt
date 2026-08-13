package com.jetbrains.kmpapp

import android.app.Application
import com.jetbrains.kmpapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class CineApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CineApp)
        }
    }
}
