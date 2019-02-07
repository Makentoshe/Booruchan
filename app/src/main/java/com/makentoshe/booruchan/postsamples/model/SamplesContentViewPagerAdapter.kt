package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.postsample.PostSampleFragment
import com.makentoshe.booruchan.postsample.PostSampleScreen

class SamplesContentViewPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        println(position)
        return PostSampleScreen().fragment
    }

    override fun getCount() = Int.MAX_VALUE
}