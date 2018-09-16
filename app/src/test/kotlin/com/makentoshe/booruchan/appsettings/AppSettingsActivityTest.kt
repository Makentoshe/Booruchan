package com.makentoshe.booruchan.appsettings

import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.fakes.RoboMenuItem

@RunWith(RobolectricTestRunner::class)
class AppSettingsActivityTest {

    private lateinit var activity: AppSettingsActivity

    @Before
    fun init() {
        activity = Robolectric.setupActivity(AppSettingsActivity::class.java)
    }

    @Test
    fun `finish activity when click on home icon`() {
        val menuItem = RoboMenuItem(android.R.id.home)
        assertTrue(activity.onOptionsItemSelected(menuItem))
        assertTrue(activity.isFinishing)
    }

    @Test
    fun `on options item selected with menu item which is not process`() {
        val menuItem = RoboMenuItem(android.R.id.background)
        assertFalse(activity.onOptionsItemSelected(menuItem))
    }

}