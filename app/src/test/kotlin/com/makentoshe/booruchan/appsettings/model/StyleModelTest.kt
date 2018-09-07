package com.makentoshe.booruchan.appsettings.model

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.widget.Spinner
import com.makentoshe.booruchan.appsettings.AppSettings
import com.makentoshe.booruchan.appsettings.AppSettingsSave
import com.makentoshe.booruchan.styles.Style
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StyleModelTest {

    private lateinit var model: StyleModel
    private lateinit var activity: AppCompatActivity
    private lateinit var appSettings: AppSettings

    @Before
    fun init() {
        activity = Robolectric.setupActivity(AppCompatActivity::class.java)

        appSettings = AppSettings()
        appSettings.setStyle(Style.Shuvi)

        val sharedPreferences = activity.getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        val appSettingsSave = AppSettingsSave(sharedPreferences, appSettings)

        model = StyleModel(activity, appSettings, appSettingsSave, mockk())
    }

    @Test
    fun `check spinner position when style is set upped`() {
        val spinner = Spinner(activity)
        model.setStyleSpinnerData(spinner)
        assertEquals(Style.ShuviName, spinner.selectedItem)
    }

    @Test
    fun `check styles count in spinner`() {
        val spinner = Spinner(activity)
        model.setStyleSpinnerData(spinner)
        assertEquals(Style.arrayOfStyleNames.size, spinner.adapter.count)
    }

}