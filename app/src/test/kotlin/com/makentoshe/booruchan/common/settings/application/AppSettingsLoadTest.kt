package com.makentoshe.booruchan.common.settings.application

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.common.styles.Style
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AppSettingsLoadTest {

    private lateinit var activity: AppCompatActivity
    private lateinit var appSettingsLoad: AppSettingsLoad
    private val obj = object: AppSettingsDumpLoad(){
        val protectedStyleKeyField = STYLE_KEY
    }
    private lateinit var appSettings: AppSettings

    @Before
    fun init() {
        activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        val sharedPreference = activity.getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        appSettings = AppSettings()
        appSettings.setStyle(Style.Shuvi)
        appSettingsLoad = AppSettingsLoad(sharedPreference, appSettings)
    }

    @Test
    fun `loading style`() {
        val sharedPreference = activity.getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        sharedPreference.edit().putInt(obj.protectedStyleKeyField, Style.Rin).apply()
        appSettingsLoad.loadStyle()
        assertEquals(Style.Rin, appSettings.getStyle().styleId)
    }

}