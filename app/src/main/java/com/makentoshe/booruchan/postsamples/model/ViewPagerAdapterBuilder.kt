package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter

interface ViewPagerAdapterBuilder {
    fun buildViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter
}