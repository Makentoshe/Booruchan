package com.makentoshe.booruchan.screen.sampleinfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post

class SampleInfoViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val post: Post
) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SampleInfoInfoFragment.create()
            1 -> SampleInfoTagsFragment.create(booru, post)
            2 -> SampleInfoCommentsFragment.create()
            else -> throw IllegalStateException()
        }
    }

    override fun getCount() = 3
}