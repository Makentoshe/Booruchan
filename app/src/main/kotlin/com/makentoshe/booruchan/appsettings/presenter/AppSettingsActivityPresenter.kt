package com.makentoshe.booruchan.appsettings.presenter

import android.widget.Spinner
import com.makentoshe.booruchan.appsettings.AppSettingsSave
import com.makentoshe.booruchan.appsettings.model.StyleModel
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivityView

class AppSettingsActivityPresenter(activity: AppSettingsActivityView) {

    private val appSettingsSave = AppSettingsSave(activity.getSharedPreferences(), activity.getApplicationSettings())
    private val styleModel = StyleModel(activity.getContext(), activity.getApplicationSettings(),
            appSettingsSave, activity.getRecreateableView())

    fun setStyleSpinnerData(spinner: Spinner) {
        styleModel.setStyleSpinnerData(spinner)
    }

    fun setStyleSpinnerListener(spinner: Spinner) {
        styleModel.setStyleSelectListener(spinner)
    }


}