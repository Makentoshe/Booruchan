package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostSamplePageInfoScreen
import com.makentoshe.booruchan.PostSamplePagePreviewScreen
import java.lang.IllegalArgumentException

class SamplePageContentPagerAdapter(
    fragmentManager: FragmentManager,
    private val sampleRepository: ImageRepository,
    private val position: Int
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> PostSamplePagePreviewScreen(sampleRepository, position).fragment
            2 -> PostSamplePageInfoScreen().fragment
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount() = 3
}