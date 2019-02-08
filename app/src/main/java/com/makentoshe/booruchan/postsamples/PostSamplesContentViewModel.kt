package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.postsamples.model.SamplesContentViewPagerAdapter
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.viewmodel.ViewModel

class PostSamplesContentViewModel : ViewModel() {
    var position = 0
        private set
    private lateinit var postsRepository: PostsRepository
    private lateinit var samplesRepository: SampleImageRepository

    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesContentViewPagerAdapter(fragmentManager, postsRepository, samplesRepository)
    }

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val samplesRepository: SampleImageRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()
            viewModel.position = position
            viewModel.postsRepository = postsRepository
            viewModel.samplesRepository = samplesRepository
            return viewModel as T
        }
    }
}