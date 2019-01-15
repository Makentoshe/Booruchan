package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.PostSamplePageScreen

class SamplePagePagerAdapter(
    fragmentManager: FragmentManager,
    private val samplePageController: SamplePageController
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = PostSamplePageScreen(position, samplePageController).fragment

    override fun getCount() = Int.MAX_VALUE
}