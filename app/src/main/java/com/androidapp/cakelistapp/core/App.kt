package com.androidapp.cakelistapp.core

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Nur Uysal on 06/12/2021.
 */
@HiltAndroidApp
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * init Timber for logging data
     */
    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}