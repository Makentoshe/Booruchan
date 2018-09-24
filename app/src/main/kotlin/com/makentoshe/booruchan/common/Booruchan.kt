package com.makentoshe.booruchan.common

import android.app.Application
import android.content.Context
import com.makentoshe.booruchan.common.settings.application.AppSettings
import com.makentoshe.booruchan.common.settings.application.AppSettingsLoad
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class Booruchan: Application() {

    @JvmField
    val appSettings: AppSettings = AppSettings()

    lateinit var refWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        refWatcher = LeakCanary.install(this)
        loadApplicationSettingsFromSharedPreferences()
    }

    private fun loadApplicationSettingsFromSharedPreferences() {
        val preferences = getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        val loader = AppSettingsLoad(preferences, appSettings)
        loader.loadStyle()
    }

}