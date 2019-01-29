package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostSamplePageScreen
import com.makentoshe.booruchan.posts.model.PostsRepository

class SamplePagePagerAdapter(
    fragmentManager: FragmentManager,
    private val samplePageController: SamplePageController,
    private val sampleRepository: ImageRepository,
    private val postsRepository: PostsRepository
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) =
        PostSamplePageScreen(position, samplePageController, sampleRepository, postsRepository).fragment

    override fun getCount() = Int.MAX_VALUE
}