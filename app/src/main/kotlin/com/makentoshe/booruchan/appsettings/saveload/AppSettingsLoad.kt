package com.makentoshe.booruchan.appsettings.saveload

import android.content.SharedPreferences
import com.makentoshe.booruchan.appsettings.AppSettings
import com.makentoshe.booruchan.styles.Style

class AppSettingsLoad(private val sharedPreferences: SharedPreferences,
                      private val appSettings: AppSettings): AppSettingsDumpLoad() {

    fun loadStyle() {
        appSettings.setStyle(sharedPreferences.getInt(STYLE_KEY, Style.Astarte))
    }

}