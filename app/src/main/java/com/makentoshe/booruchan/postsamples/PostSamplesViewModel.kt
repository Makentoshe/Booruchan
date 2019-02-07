package com.makentoshe.booruchan.postsamples

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.postsamples.model.SamplesVerticalViewPagerAdapter
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel

class PostSamplesViewModel private constructor() : ViewModel() {
    private var pagePosition: Int = 0
    private var itemPosition: Int = 0
    private lateinit var postsRepository: PostsRepository
    lateinit var verticalViewPagerAdapter: SamplesVerticalViewPagerAdapter
        private set

    fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        return true
    }

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val fragmentManager: FragmentManager
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesViewModel()
            viewModel.pagePosition = position / 12
            viewModel.itemPosition = position % 12
            viewModel.postsRepository = postsRepository

            viewModel.verticalViewPagerAdapter =
                    SamplesVerticalViewPagerAdapter(fragmentManager, position, postsRepository)

            return viewModel as T
        }
    }
}