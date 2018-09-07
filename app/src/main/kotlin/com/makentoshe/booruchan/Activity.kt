package com.makentoshe.booruchan

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.appsettings.AppSettings

@SuppressLint("Registered")
abstract class Activity: AppCompatActivity() {

    fun getAppSettings(): AppSettings {
        return (application as Booruchan).appSettings
    }

    private var currentStyleVal: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        currentStyleVal = getAppSettings().getStyleVal()
        setTheme(currentStyleVal)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (currentStyleVal != getAppSettings().getStyleVal()) {
            recreate()
        }
    }

}