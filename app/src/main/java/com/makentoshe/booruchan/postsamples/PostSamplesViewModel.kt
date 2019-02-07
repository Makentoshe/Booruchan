package com.makentoshe.booruchan.postsamples

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.postsamples.model.SamplesVerticalViewPagerAdapter
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel

class PostSamplesViewModel private constructor() : ViewModel() {
    private var position: Int = 0
    private lateinit var postsRepository: PostsRepository

    fun getSamplesVerticalViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesVerticalViewPagerAdapter(fragmentManager, position, postsRepository)
    }

    fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        return true
    }

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesViewModel()
            viewModel.position = position
            viewModel.postsRepository = postsRepository
            return viewModel as T
        }
    }
}