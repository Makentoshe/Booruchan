package com.makentoshe.booruchan.appsettings.saveload

import android.content.SharedPreferences
import com.makentoshe.booruchan.appsettings.AppSettings

class AppSettingsSave(private val sharedPreferences: SharedPreferences,
                      private val appSettings: AppSettings): AppSettingsDumpLoad() {

    fun saveStyle() {
        sharedPreferences.edit().putInt(STYLE_KEY, appSettings.getStyleVal()).apply()
    }

}