package com.makentoshe.booruchan.screen.posts.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.screen.posts.PostsPageFragment

class PostsViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val tags: Set<Tag>,
    private val booru: Booru,
    private var count: Int = Int.MAX_VALUE
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PostsPageFragment.create(position, booru, tags)
    }

    override fun getCount() = count

    fun copy(count: Int = getCount()): PostsViewPagerAdapter {
        return PostsViewPagerAdapter(fragmentManager, tags, booru, count)
    }
}