package com.makentoshe.booruchan.postsamples

import androidx.lifecycle.ViewModelProvider
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel

class PostSamplesViewModel private constructor() : ViewModel() {
    private var pagePosition: Int = 0
    private var itemPosition: Int = 0
    private lateinit var postsRepository: PostsRepository

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesViewModel()
            viewModel.pagePosition = position / 12
            viewModel.itemPosition = position % 12
            viewModel.postsRepository = postsRepository
            return viewModel as T
        }
    }
}