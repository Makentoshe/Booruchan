package com.makentoshe.booruchan.screen.sampleinfo.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.sampleinfo.fragment.SampleInfoCommentsFragment
import com.makentoshe.booruchan.screen.sampleinfo.fragment.SampleInfoInfoFragment
import com.makentoshe.booruchan.screen.sampleinfo.fragment.SampleInfoTagsFragment

class SampleInfoViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val post: Post
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SampleInfoInfoFragment.create(booru, post)
            1 -> SampleInfoTagsFragment.create(booru, post)
            2 -> SampleInfoCommentsFragment.create(booru, post)
            else -> throw IllegalStateException()
        }
    }

    override fun getCount() = 3
}