package com.makentoshe.booruchan.booru.view

import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BooruActivityUIContentTest {

    @Test
    fun `click on alpha view simple test`() {
        val activityController = Robolectric.buildActivity(BooruActivity::class.java)
        activityController.get().intent.putExtra(BooruActivity.BOORU_EXTRA, Gelbooru::class.java.simpleName)
        val activity = activityController.setup().get()
        activity.findViewById<View>(R.id.booru_content_alpha).performClick()
    }

}