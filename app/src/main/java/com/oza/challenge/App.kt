package com.oza.challenge

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    companion object {

        private lateinit var instance: App

        fun getAppContext(): Context {
            return instance.applicationContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        // Optional: Add any additional configurations or initialization code here
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        instance = this
    }


}



