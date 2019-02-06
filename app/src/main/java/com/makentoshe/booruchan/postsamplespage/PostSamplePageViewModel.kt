package com.makentoshe.booruchan.postsamplespage

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.FragmentViewModel
import com.makentoshe.booruchan.postsamples.model.SamplePageContentPagerAdapter
import com.makentoshe.booruchan.postsamples.model.SamplePageHorizontalScrollBlockController
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository

class PostSamplePageViewModel : com.makentoshe.viewmodel.ViewModel() {
    private var position: Int = -1
    private lateinit var samplePageHorizontalScrollBlockController: SamplePageHorizontalScrollBlockController
    private lateinit var sampleRepository: ImageRepository
    private lateinit var postsRepository: PostsRepository

    fun block() =
        samplePageHorizontalScrollBlockController.newState(SamplePageHorizontalScrollBlockController.Command.BLOCK)

    fun unblock() =
        samplePageHorizontalScrollBlockController.newState(SamplePageHorizontalScrollBlockController.Command.UNBLOCK)

    fun backToPreviews() =
        samplePageHorizontalScrollBlockController.newState(SamplePageHorizontalScrollBlockController.Command.CLOSE)

    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplePageContentPagerAdapter(fragmentManager, sampleRepository, position, postsRepository)
    }

    class Factory(
        private val position: Int,
        private val samplePageHorizontalScrollBlockController: SamplePageHorizontalScrollBlockController,
        private val sampleRepository: ImageRepository,
        private val postsRepository: PostsRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplePageViewModel()
            viewModel.position = position
            viewModel.samplePageHorizontalScrollBlockController = samplePageHorizontalScrollBlockController
            viewModel.sampleRepository = sampleRepository
            viewModel.postsRepository = postsRepository
            return viewModel as T
        }
    }

}