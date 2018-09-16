package com.makentoshe.booruchan.common.settings.application

import android.content.SharedPreferences

class AppSettingsSave(private val sharedPreferences: SharedPreferences,
                      private val appSettings: AppSettings): AppSettingsDumpLoad() {

    fun saveStyle() {
        sharedPreferences.edit().putInt(STYLE_KEY, appSettings.getStyleVal()).apply()
    }

}