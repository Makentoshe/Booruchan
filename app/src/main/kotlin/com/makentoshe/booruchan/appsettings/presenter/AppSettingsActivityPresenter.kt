package com.makentoshe.booruchan.appsettings.presenter

import android.widget.AdapterView
import android.widget.Spinner
import com.makentoshe.booruchan.common.settings.application.AppSettingsSave
import com.makentoshe.booruchan.appsettings.model.StyleModel
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivityView

class AppSettingsActivityPresenter(activity: AppSettingsActivityView) {

    private val appSettingsSave = AppSettingsSave(activity.getSharedPreferences(), activity.getApplicationSettings())
    private val styleModel = StyleModel(activity.getContext(), activity.getApplicationSettings(),
            appSettingsSave, activity.getRecreateableView())

    fun setStyleSpinnerData(spinner: Spinner) {
        styleModel.setStyleSpinnerData(spinner)
    }

    fun setStyleOnSelect(adapterView: AdapterView<*>, position: Int) {
        styleModel.setStyleOnSelect(adapterView, position)
    }


}