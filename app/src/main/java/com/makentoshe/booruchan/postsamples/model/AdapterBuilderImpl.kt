package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.postsamples.FullScreenController

class AdapterBuilderImpl(
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val fullScreenController: FullScreenController
) : AdapterBuilder {
//TODO make it view model may be
    override fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesContentViewPagerAdapter(fragmentManager, booru, tags, fullScreenController)
    }
}