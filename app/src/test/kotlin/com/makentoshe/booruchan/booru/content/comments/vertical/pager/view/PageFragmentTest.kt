package com.makentoshe.booruchan.booru.content.comments.vertical.pager.view

import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.view.BooruActivity
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PageFragmentTest {

    @Test
    fun test() {
        val activityController = Robolectric.buildActivity(BooruActivity::class.java)
        activityController.get().intent.putExtra(BooruActivity.BOORU_EXTRA, Gelbooru::class.java.simpleName)
        val activity = activityController.setup().get()
        val fragment = PageFragment.new(2)
        activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.booru_content_container, fragment, "SAS")
                .commitNow()
        assertNotNull(activity.supportFragmentManager.findFragmentByTag("SAS"))
        assertNotNull(fragment.view)
    }

}