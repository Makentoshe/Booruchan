package com.makentoshe.booruchan.common.settings.application

import android.content.SharedPreferences
import com.makentoshe.booruchan.common.styles.Style

class AppSettingsLoad(private val sharedPreferences: SharedPreferences,
                      private val appSettings: AppSettings): AppSettingsDumpLoad() {

    fun loadStyle() {
        appSettings.setStyle(sharedPreferences.getInt(STYLE_KEY, Style.Astarte))
    }

}