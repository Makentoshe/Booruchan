package com.makentoshe.booruchan.postsamples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostSamplePageInfoScreen
import com.makentoshe.booruchan.PostSamplePagePreviewScreen
import com.makentoshe.booruchan.posts.model.PostsRepository

class SamplePageContentPagerAdapter(
    fragmentManager: FragmentManager,
    private val sampleRepository: ImageRepository,
    private val position: Int,
    private val postsRepository: PostsRepository
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> PostSamplePagePreviewScreen(sampleRepository, this.position, postsRepository).fragment
            2 -> PostSamplePageInfoScreen(this.position, postsRepository).fragment
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount() = 3
}