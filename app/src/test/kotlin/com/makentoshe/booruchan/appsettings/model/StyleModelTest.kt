package com.makentoshe.booruchan.appsettings.model

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.Spinner
import com.makentoshe.booruchan.common.settings.application.AppSettings
import com.makentoshe.booruchan.common.settings.application.AppSettingsSave
import com.makentoshe.booruchan.appsettings.view.RecreateableView
import com.makentoshe.booruchan.common.styles.Style
import io.mockk.every
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

        val recreateableView = mockk<RecreateableView>()
        every { recreateableView.recreate() } returns Unit

        model = StyleModel(activity, appSettings, appSettingsSave, recreateableView)
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


    @Test
    fun `check style changing`() {
        val spinner = Spinner(activity)
        model.setStyleSpinnerData(spinner)
        spinner.setSelection(0)
        val adapterView = mockk<AdapterView<*>>()
        every { adapterView.getItemAtPosition(0) } returns Style.AstarteName
        model.setStyleOnSelect(adapterView, 0)
        assertEquals(Style.Astarte, appSettings.getStyleVal())
    }

}