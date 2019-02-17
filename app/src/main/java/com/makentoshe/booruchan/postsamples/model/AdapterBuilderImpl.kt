package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru

class AdapterBuilderImpl(private val booru: Booru):
    AdapterBuilder {
    override fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesContentViewPagerAdapter(fragmentManager, booru)
    }
}