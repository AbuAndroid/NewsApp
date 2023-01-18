package com.example.viewmodellivedata

import android.app.Application
import com.example.viewmodellivedata.di.module.appModule
import com.example.viewmodellivedata.di.module.repomodule
import com.example.viewmodellivedata.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppMain:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppMain)
            modules(listOf(appModule, repomodule, viewModelModule))
        }
    }
}