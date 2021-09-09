package com.olesix.favmusic

import android.app.Application
import com.olesix.favmusic.di.*
import com.olesix.favmusic.di.mainViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinApp)
            modules(databaseModule, networkModule, mainViewModule, repositoryModule,
                dataSourceModule)
        }
    }
}