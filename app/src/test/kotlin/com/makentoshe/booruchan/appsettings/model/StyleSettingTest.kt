package com.makentoshe.booruchan.appsettings.model

import android.content.Context
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.settings.application.AppSettings
import com.makentoshe.booruchan.common.settings.application.AppSettingsDumpLoad
import com.makentoshe.booruchan.common.settings.application.AppSettingsSave
import com.makentoshe.booruchan.common.styles.AstarteStyle
import com.makentoshe.booruchan.common.styles.RinStyle
import com.makentoshe.booruchan.common.styles.ShuviStyle
import com.makentoshe.booruchan.common.styles.Style
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StyleSettingTest {

    class ActivityImpl: Activity()

    private val styleSetting = StyleSetting()
    private lateinit var activity: ActivityImpl

    @Before
    fun init() {
        activity = Robolectric.setupActivity(ActivityImpl::class.java)
        activity.getAppSettings().setStyle(ShuviStyle().styleId)
    }

    @Test
    fun `spinner adapter creation`() {
        val adapter = styleSetting.createSpinnerAdapter(activity)
        assertNotNull(adapter)
        assertFalse(adapter.isEmpty)
    }

    @Test
    fun `style selection`() {
        val obj = object : AppSettingsDumpLoad() {
            val key = STYLE_KEY
        }

        val styleToSelect = RinStyle()
        val sharedPreferences = activity.getSharedPreferences(AppSettings.NAME, Context.MODE_PRIVATE)
        val appSettingsSave = AppSettingsSave(sharedPreferences, activity.getAppSettings())
        styleSetting.styleSelected(styleToSelect, activity, appSettingsSave)

        assertEquals(styleToSelect.styleId, activity.getAppSettings().getStyle().styleId)
        assertEquals(styleToSelect.styleId, sharedPreferences.getInt(obj.key, Style.Astarte))
    }

}