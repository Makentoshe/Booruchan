package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.postsamples.model.SamplesContentViewPagerAdapter
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel

class PostSamplesContentViewModel : ViewModel() {
    var position = 0
        private set
    private lateinit var postsRepository: PostsRepository

    fun getViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesContentViewPagerAdapter(fragmentManager, postsRepository)
    }

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()
            viewModel.position = position
            viewModel.postsRepository = postsRepository
            return viewModel as T
        }
    }
}