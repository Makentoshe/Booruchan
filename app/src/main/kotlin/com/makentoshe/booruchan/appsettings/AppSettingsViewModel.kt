package com.makentoshe.booruchan.appsettings

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.widget.SpinnerAdapter
import com.makentoshe.booruchan.appsettings.model.StyleSetting
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.settings.application.AppSettings
import com.makentoshe.booruchan.common.settings.application.AppSettingsSave
import com.makentoshe.booruchan.common.styles.Style

class AppSettingsViewModel: ViewModel() {

    private val styleSetting: StyleSetting by lazy {
        StyleSetting()
    }

    fun createStyleSpinnerAdapter(context: Context): SpinnerAdapter {
        return styleSetting.createSpinnerAdapter(context)
    }

    fun onStyleSelected(activity: Activity, styleTitle: String) {
        val sharedPreferences = activity.getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        val appSettingsSave = AppSettingsSave(sharedPreferences, activity.getAppSettings())
        val style = Style.getStyleByName(styleTitle)
        styleSetting.styleSelected(style, activity, appSettingsSave)
    }

}