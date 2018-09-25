package com.makentoshe.booruchan.appsettings.model

import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.styles.ShuviStyle
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
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

}