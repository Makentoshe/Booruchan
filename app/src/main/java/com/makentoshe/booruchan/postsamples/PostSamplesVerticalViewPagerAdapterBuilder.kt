package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.postsamples.model.SamplesVerticalViewPagerAdapter
import com.makentoshe.booruchan.postsamples.model.ViewPagerAdapterBuilder
import com.makentoshe.viewmodel.ViewModel

class PostSamplesVerticalViewPagerAdapterBuilder(
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int
) : ViewModel(), ViewPagerAdapterBuilder {

    override fun buildViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesVerticalViewPagerAdapter(fragmentManager, booru, tags, position)
    }
}