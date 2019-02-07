package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruchan.postsamples.PostSampleFragment

class SamplesContentViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return PostSampleFragment()
    }

    override fun getCount() = Int.MAX_VALUE
}