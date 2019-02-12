package com.makentoshe.booruchan.postpreviews.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpreviews.PostPageScreen
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.PreviewImageRepository
import com.makentoshe.repository.Repository
import com.makentoshe.repository.SampleImageRepository

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val postsRepository: Repository<Booru.PostRequest, Posts>,
    private val previewsRepository: Repository<String, ByteArray>,
    private val samplesRepository: SampleImageRepository
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) =
        PostPageScreen(booru, position, postsRepository, previewsRepository, samplesRepository).fragment

    override fun getCount() = Int.MAX_VALUE
}