package com.makentoshe.booruchan.postpreviews.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.PostPageScreen
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.PreviewImageRepository

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val postsRepository: PostsRepository,
    private val previewsRepository: PreviewImageRepository
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) =
        PostPageScreen(booru, position, postsRepository, previewsRepository).fragment

    override fun getCount() = Int.MAX_VALUE
}