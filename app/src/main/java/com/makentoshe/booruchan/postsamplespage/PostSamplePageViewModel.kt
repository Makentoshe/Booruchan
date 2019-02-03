package com.makentoshe.booruchan.postsamplespage

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.FragmentViewModel
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostsRepository
import com.makentoshe.booruchan.postsamples.model.SamplePageHorizontalScrollBlockController
import com.makentoshe.booruchan.postsamples.model.SamplePageContentPagerAdapter

class PostSamplePageViewModel(
    private val position: Int,
    private val samplePageHorizontalScrollBlockController: SamplePageHorizontalScrollBlockController,
    private val sampleRepository: ImageRepository,
    private val postsRepository: PostsRepository
) : FragmentViewModel() {

    fun block() = samplePageHorizontalScrollBlockController.newState(SamplePageHorizontalScrollBlockController.Command.BLOCK)

    fun unblock() = samplePageHorizontalScrollBlockController.newState(SamplePageHorizontalScrollBlockController.Command.UNBLOCK)

    fun backToPreviews() = samplePageHorizontalScrollBlockController.newState(SamplePageHorizontalScrollBlockController.Command.CLOSE)

    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplePageContentPagerAdapter(
            fragmentManager,
            sampleRepository,
            position,
            postsRepository,
            samplePageHorizontalScrollBlockController
        )
    }
}