package com.makentoshe.booruchan.screen.samples.controller

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.screen.samples.model.SampleHorizontalViewPagerAdapter
import org.jetbrains.anko.find

/**
 * Controller for the [SampleFragment].
 */
class SampleController(
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int,
    private val fragmentManager: FragmentManager
) {

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.samples_container_viewpager)
        //adapter for horizontal scrolling
        viewpager.adapter =
            SampleHorizontalViewPagerAdapter(fragmentManager, booru, tags)
        //tags adapter to the position on which the click event was invoked
        viewpager.currentItem = position
    }
}