package com.makentoshe.booruchan.common

import android.app.Application
import android.content.Context
import com.makentoshe.booruchan.common.settings.application.AppSettings
import com.makentoshe.booruchan.common.settings.application.AppSettingsLoad

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