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
class AppSettingsSaveTest {

    private lateinit var activity: AppCompatActivity
    private lateinit var appSettingsSave: AppSettingsSave
    private val obj = object: AppSettingsDumpLoad(){
        val protectedStyleKeyField = STYLE_KEY
    }

    @Before
    fun init() {
        activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        val sharedPreference = activity.getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        val appSettings = AppSettings()
        appSettings.setStyle(Style.Shuvi)
        appSettingsSave = AppSettingsSave(sharedPreference, appSettings)
    }

    @Test
    fun `style saving`() {
        appSettingsSave.saveStyle()
        val sharedPreference = activity.getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        assertEquals(Style.Shuvi, sharedPreference.getInt(obj.protectedStyleKeyField, -1))
    }
}