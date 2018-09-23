package com.makentoshe.booruchan.booru.view

import android.arch.lifecycle.ViewModelProviders
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.fakes.RoboMenuItem

@RunWith(RobolectricTestRunner::class)
class BooruActivityTest {

    private lateinit var activity: BooruActivity

    @Before
    fun init() {
        val activityController = Robolectric.buildActivity(BooruActivity::class.java)
        activityController.get().intent.putExtra(BooruActivity.BOORU_EXTRA, Gelbooru::class.java.simpleName)
        activity = activityController.setup().get()
    }


    @Test
    fun `simple test with toolbar overflow menu`() {
        val item = RoboMenuItem()
        item.itemId = R.id.action_show_search
        assertTrue(activity.onOptionsItemSelected(item))
    }

    @Test
    fun `simple test with toolbar overflow menu and incorrect item id`() {
        val item = RoboMenuItem()
        item.itemId = -1
        assertFalse(activity.onOptionsItemSelected(item))
    }

    @Test
    fun `on back pressed default`() {
        val shadowActivity = Shadows.shadowOf(activity)
        activity.onBackPressed()
        assertTrue(shadowActivity.isFinishing)
    }

    @Test
    fun `on back pressed when alpha label is visible should hide it`() {
        activity.findViewById<View>(R.id.booru_content_alpha).visibility = View.VISIBLE
        val shadowActivity = Shadows.shadowOf(activity)
        activity.onBackPressed()
        assertFalse(shadowActivity.isFinishing)
    }

}