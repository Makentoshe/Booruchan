package com.makentoshe.booruchan.start.view

import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.fakes.RoboMenu
import org.robolectric.fakes.RoboMenuItem

@RunWith(RobolectricTestRunner::class)
class StartActivityTest {

    private lateinit var activity: StartActivity

    @Before
    fun init() {
        activity = Robolectric.setupActivity(StartActivity::class.java)
    }

    @Test
    fun `test overflow menu with correct id`() {
        val menuItem = RoboMenuItem(R.id.action_app_settings)
        assertTrue(activity.onOptionsItemSelected(menuItem))
        val shadowActivity = Shadows.shadowOf(activity)
        val intent = shadowActivity.nextStartedActivityForResult
        Assert.assertEquals(intent.intent.component!!.className, AppSettingsActivity::class.java.name)
    }

    @Test
    fun `test overflow menu creating`() {
        val menu = RoboMenu(activity)
        assertTrue(activity.onCreateOptionsMenu(menu))
        val item = menu.findItem(R.id.action_app_settings)
        assertNotNull(item)
        assertEquals(activity.getString(R.string.app_settings), item.title)
    }
}