package com.makentoshe.booruchan.posts.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.PostPageScreen

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val tags: Set<Tag>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = PostPageScreen(booru, position, tags.toHashSet()).fragment

    override fun getCount() = Int.MAX_VALUE
}