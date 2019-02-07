package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.postsamples.model.SamplesContentViewPagerAdapter
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel

class PostSamplesContentViewModel : ViewModel() {
    var position = 0
        private set
    lateinit var viewPagerAdapter: SamplesContentViewPagerAdapter
        private set

    class Factory(
        private val position: Int,
        private val fragmentManager: FragmentManager,
        private val postsRepository: PostsRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()
            viewModel.position = position
            viewModel.viewPagerAdapter =
                    SamplesContentViewPagerAdapter(fragmentManager, postsRepository)
            return viewModel as T
        }
    }
}