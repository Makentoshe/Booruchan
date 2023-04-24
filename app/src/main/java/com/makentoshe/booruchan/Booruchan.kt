package com.makentoshe.booruchan

import android.app.Application
import com.makentoshe.booruchan.library.logging.initializeDebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Booruchan : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeDebugLogger()
    }
}