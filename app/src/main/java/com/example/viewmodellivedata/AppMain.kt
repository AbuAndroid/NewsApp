package com.example.viewmodellivedata

import android.app.Application
import com.example.viewmodellivedata.di.module.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppMain:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppMain)
            modules(Test.modules())
        }
    }
}