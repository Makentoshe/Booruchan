package com.makentoshe.booruchan.booru

import android.content.Intent
import android.support.v7.view.menu.ActionMenuItemView
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.view.BooruActivity
import com.makentoshe.booruchan.booru.view.BooruActivity.Companion.BOORU_EXTRA
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
class BooruViewModelTest {

    private val viewModel = BooruViewModel(Gelbooru())

    @Test
    fun `should contain booru`() {
        assertNotNull(viewModel.booru)
    }

    @Test
    fun `should change search label state`() {
        val activityController = Robolectric.buildActivity(BooruActivity::class.java)
        activityController.get().intent = Intent().putExtra(BOORU_EXTRA, Gelbooru::class.java.simpleName)
        val activity = activityController.setup().get()

        assertEquals(View.GONE, activity.findViewById<View>(R.id.booru_content_alpha).visibility)
        assertEquals(View.GONE, activity.findViewById<View>(R.id.booru_content_search).visibility)

        viewModel.changeSearchLabelState(activity, activity.getAppSettings().getStyle())

        assertEquals(View.VISIBLE, activity.findViewById<View>(R.id.booru_content_alpha).visibility)
        assertEquals(View.VISIBLE, activity.findViewById<View>(R.id.booru_content_search).visibility)
    }

}