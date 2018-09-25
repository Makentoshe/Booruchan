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

}