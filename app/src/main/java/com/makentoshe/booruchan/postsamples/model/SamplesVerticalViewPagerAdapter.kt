package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.postsamples.PostSamplesContentFragment
import com.makentoshe.booruchan.postsamples.PostSamplesContentScreen

class SamplesVerticalViewPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> Fragment()
            1 -> PostSamplesContentScreen().fragment
            else -> throw IllegalArgumentException(position.toString())
        }
    }

    override fun getCount() = 2
}