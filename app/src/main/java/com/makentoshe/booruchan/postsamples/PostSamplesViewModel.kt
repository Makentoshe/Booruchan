package com.makentoshe.booruchan.postsamples

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruchan.postsamples.model.SamplesVerticalViewPagerAdapter
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.viewmodel.ViewModel
import ru.terrakok.cicerone.Router

class PostSamplesViewModel private constructor() : ViewModel() {

    private var position: Int = 0
    private lateinit var postsRepository: PostsRepository
    private lateinit var samplesRepository: SampleImageRepository
    private lateinit var router: Router

    fun onDownloadIconClick(ignored: View) {
        println("Click")
    }


    fun getSamplesVerticalViewPagerAdapter(fragmentManager: FragmentManager): PagerAdapter {
        return SamplesVerticalViewPagerAdapter(fragmentManager, position, postsRepository, samplesRepository)
    }

    fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        return true
    }

    /* Returns to the previous screen. Same as onBackButton press. */
    fun exit() = router.exit()

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val samplesRepository: SampleImageRepository,
        private val router: Router
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesViewModel()
            viewModel.position = position
            viewModel.postsRepository = postsRepository
            viewModel.samplesRepository = samplesRepository
            viewModel.router = router
            return viewModel as T
        }
    }
}