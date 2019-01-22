package com.makentoshe.booruchan.postsamplespage

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.postsamples.model.SamplePageContentPagerAdapter
import com.makentoshe.booruchan.postsamples.model.SamplePageController

class PostSamplePageViewModel(
    private val position: Int,
    private val samplePageController: SamplePageController,
    private val sampleRepository: ImageRepository
) : ViewModel() {

    fun block() = samplePageController.block()

    fun unblock() = samplePageController.unblock()

    fun backToPreviews() = samplePageController.backToPreviews()

    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplePageContentPagerAdapter(fragmentManager, sampleRepository, position)
    }
}