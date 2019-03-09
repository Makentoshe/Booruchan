package com.makentoshe.booruchan.screen.samples.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruchan.screen.samples.SamplePageFragment

class SampleHorizontalViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int) = SamplePageFragment.create(position)

    override fun getCount() = Int.MAX_VALUE

    override fun saveState() = null
}