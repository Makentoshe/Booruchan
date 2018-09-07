package com.makentoshe.booruchan.start

import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.appsettings.view.AppSettingsActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.fakes.RoboMenuItem
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
class StartActivityTest {

    @Test
    fun `test overflow menu`() {
        val activity = Robolectric.setupActivity(StartActivity::class.java)
        val menuItem = RoboMenuItem(R.id.action_app_settings)
        assertTrue(activity.onOptionsItemSelected(menuItem))
        val shadowActivity = Shadows.shadowOf(activity)
        val intent = shadowActivity.peekNextStartedActivity()
        assertEquals(AppSettingsActivity::class.java.name, intent.component!!.className)
    }

}