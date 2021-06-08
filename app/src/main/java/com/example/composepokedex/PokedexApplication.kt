package com.example.composepokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

class PokedexApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}