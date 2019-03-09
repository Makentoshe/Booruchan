package com.makentoshe.booruchan.screen.samples

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SampleVerticalViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> SampleScreen().fragment
            else -> throw IllegalArgumentException(position.toString())
        }
    }

    override fun getCount() = 2

    override fun saveState() = null
}