package com.makentoshe.booruchan.start

import com.makentoshe.booruchan.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.fakes.RoboMenuItem

@RunWith(RobolectricTestRunner::class)
class StartActivityTest {

    @Test
    fun `test overflow menu`() {
        val activity = Robolectric.setupActivity(StartActivity::class.java)
        val menuItem = RoboMenuItem(R.id.action_app_settings)
        activity.onOptionsItemSelected(menuItem)
    }

}