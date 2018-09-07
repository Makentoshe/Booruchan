package com.makentoshe.booruchan.appsettings.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.makentoshe.booruchan.Activity
import com.makentoshe.booruchan.appsettings.AppSettings
import com.makentoshe.booruchan.appsettings.presenter.AppSettingsActivityPresenter
import org.jetbrains.anko.setContentView

class AppSettingsActivity: Activity(), AppSettingsActivityView, RecreateableView {

    private lateinit var presenter: AppSettingsActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = AppSettingsActivityPresenter(this)
        super.onCreate(savedInstanceState)
        AppSettingsActivityUI(getAppSettings().getStyle(), presenter).setContentView(this)
    }


    override fun getApplicationSettings(): AppSettings {
        return getAppSettings()
    }

    override fun getContext(): Context {
        return this
    }

    override fun getSharedPreferences(): SharedPreferences {
        return getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
    }

    override fun getRecreateableView(): RecreateableView {
        return this
    }

}