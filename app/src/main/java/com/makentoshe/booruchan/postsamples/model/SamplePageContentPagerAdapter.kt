package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.PostSamplePageInfoScreen
import com.makentoshe.booruchan.PostSamplePagePreviewScreen
import java.lang.IllegalArgumentException

class SamplePageContentPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> PostSamplePagePreviewScreen().fragment
            2 -> PostSamplePageInfoScreen().fragment
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount() = 3
}