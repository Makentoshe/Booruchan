package com.makentoshe.booruchan.postsamplespage

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostsRepository
import com.makentoshe.booruchan.postsamples.model.SamplePageBlockController
import com.makentoshe.booruchan.postsamples.model.SamplePageContentPagerAdapter

class PostSamplePageViewModel(
    private val position: Int,
    private val samplePageController: SamplePageBlockController,
    private val sampleRepository: ImageRepository,
    private val postsRepository: PostsRepository
) : ViewModel() {

    fun block() = samplePageController.newState(SamplePageBlockController.Command.BLOCK)

    fun unblock() = samplePageController.newState(SamplePageBlockController.Command.UNBLOCK)

    fun backToPreviews() = samplePageController.newState(SamplePageBlockController.Command.CLOSE)

    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplePageContentPagerAdapter(fragmentManager, sampleRepository, position, postsRepository)
    }
}