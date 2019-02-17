package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter

interface AdapterBuilder {
    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter
}