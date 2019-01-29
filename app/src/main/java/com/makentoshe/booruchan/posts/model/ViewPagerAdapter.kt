package com.makentoshe.booruchan.posts.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.PostPageScreen

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val postsRepository: PostsRepository,
    private val previewsRepository: PreviewsRepository
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) =
        PostPageScreen(booru, position, postsRepository, previewsRepository).fragment

    override fun getCount() = Int.MAX_VALUE
}