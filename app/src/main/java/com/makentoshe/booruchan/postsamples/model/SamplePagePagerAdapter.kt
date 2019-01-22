package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostSamplePageScreen

class SamplePagePagerAdapter(
    fragmentManager: FragmentManager,
    private val samplePageController: SamplePageController,
    private val sampleRepository: ImageRepository
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = PostSamplePageScreen(position, samplePageController, sampleRepository).fragment

    override fun getCount() = Int.MAX_VALUE
}