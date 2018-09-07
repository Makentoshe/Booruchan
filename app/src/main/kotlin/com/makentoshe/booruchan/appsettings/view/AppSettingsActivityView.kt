package com.makentoshe.booruchan.appsettings.view

import android.content.Context
import android.content.SharedPreferences
import com.makentoshe.booruchan.appsettings.AppSettings

interface AppSettingsActivityView {

    fun getApplicationSettings(): AppSettings

    fun getContext(): Context

    fun getSharedPreferences(): SharedPreferences

    fun getRecreateableView(): RecreateableView

}