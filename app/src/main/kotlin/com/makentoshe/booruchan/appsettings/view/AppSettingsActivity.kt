package com.makentoshe.booruchan.appsettings.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.settings.application.AppSettings
import com.makentoshe.booruchan.appsettings.presenter.AppSettingsActivityPresenter
import org.jetbrains.anko.setContentView

class AppSettingsActivity: Activity(), AppSettingsActivityView, RecreateableView {

    private lateinit var presenter: AppSettingsActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = AppSettingsActivityPresenter(this)
        super.onCreate(savedInstanceState)
        AppSettingsActivityUI(getAppSettings().getStyle(), presenter).setContentView(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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