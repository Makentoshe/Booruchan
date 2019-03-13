package com.makentoshe.booruchan.screen.posts.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.screen.posts.PostsPageScreen

class PostsViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val tags: Set<Tag>,
    private val booru: Booru
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return PostsPageScreen(position, booru, tags).fragment
    }

    override fun getCount() = Int.MAX_VALUE
}