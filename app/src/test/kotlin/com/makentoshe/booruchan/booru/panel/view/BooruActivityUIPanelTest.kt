package com.makentoshe.booruchan.booru.panel.view

import android.view.View
import android.widget.ListView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.view.BooruActivity
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.jetbrains.anko.childrenSequence
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BooruActivityUIPanelTest {

    private lateinit var activity: BooruActivity

    @Before
    fun init() {
        val activityController = Robolectric.buildActivity(BooruActivity::class.java)
        activityController.get().intent.putExtra(BooruActivity.BOORU_EXTRA, Gelbooru::class.java.simpleName)
        activity = activityController.setup().get()
    }

    @Test
    fun `click on panel view simple test`() {
        val view = activity.findViewById<View>(R.id.booru_panel_background_image).parent as View
        view.performClick()
    }

    @Test
    fun `click on list view element simple test`() {
        val view = activity.findViewById<View>(R.id.booru_panel_background_image).parent as View
        for (child in view.childrenSequence()) {
            if (child is ListView) {
                child.performItemClick(
                        child.adapter.getView(0, null, null),
                        0,
                        child.adapter.getItemId(0))
            }
        }
    }

}