package com.makentoshe.booruchan.appsettings.model

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.settings.application.AppSettingsSave
import com.makentoshe.booruchan.common.styles.Style

class StyleSetting {

    fun createSpinnerAdapter(context: Context): SpinnerAdapter {
        return ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Style.arrayOfStyleNames)
    }

    fun styleSelected(style: Style, activity: Activity, appSettingsSave: AppSettingsSave) {
        if (activity.getAppSettings().getStyle().styleId != style.styleId) {
            activity.getAppSettings().setStyle(style.styleId)
            appSettingsSave.saveStyle()
            activity.recreate()
        }
    }

}