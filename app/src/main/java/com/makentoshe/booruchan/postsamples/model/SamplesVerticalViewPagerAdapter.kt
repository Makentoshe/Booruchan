package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruchan.postsamples.PostSamplesContentScreen

class SamplesVerticalViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val position: Int
): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> Fragment()
            1 -> PostSamplesContentScreen(this.position).fragment
            else -> throw IllegalArgumentException(position.toString())
        }
    }

    override fun getCount() = 2
}