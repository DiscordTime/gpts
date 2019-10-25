package com.discordtime.gpts

import android.app.Application
import com.discordtime.gpts.di.placeRepositoryModule
import com.discordtime.gpts.di.remoteModules
import com.discordtime.gpts.di.viewModelModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this@Application)

        startKoin { androidContext(this@Application)
            modules(listOf(remoteModules, viewModelModule, placeRepositoryModule))
        }

    }
}