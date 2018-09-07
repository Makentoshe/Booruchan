package com.makentoshe.booruchan

import android.app.Application
import android.content.Context
import com.makentoshe.booruchan.appsettings.AppSettings
import com.makentoshe.booruchan.appsettings.AppSettingsLoad

class Booruchan: Application() {

    val appSettings: AppSettings = AppSettings()

    override fun onCreate() {
        super.onCreate()
        loadApplicationSettingsFromSharedPreferences()
    }

    private fun loadApplicationSettingsFromSharedPreferences() {
        val preferences = getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        val loader = AppSettingsLoad(preferences, appSettings)
        loader.loadStyle()
    }

}