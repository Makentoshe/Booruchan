package com.makentoshe.booruchan.postpreviews

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.booru.model.DrawerState
import com.makentoshe.booruchan.postpreviews.model.*
import com.makentoshe.repository.CachedRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.PreviewImageRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.repository.cache.Cache

class PostsFragmentViewModel : com.makentoshe.viewmodel.ViewModel() {
    private lateinit var booru: Booru
    private lateinit var drawerController: DrawerController
    /* tags for default(on the startup) search */
    private lateinit var tags: Set<Tag>
    private lateinit var viewPagerController: ViewPagerController

    val booruTitle: String
        get() = booru.title

    fun gotoPage(page: Int) = viewPagerController.action(page)

    fun gotoNextPage() = viewPagerController.nextPage()

    fun gotoPrevPage() = viewPagerController.prevPage()

    fun onPageChangeListener(action: (Int) -> Unit) = viewPagerController.subscribe(action)

    val autocompleteAdapter: DelayAutocompleteAdapter
        get() = DelayAutocompleteAdapter(DelayAutocompleteRepository(booru))

    override fun onCreateView(owner: Fragment) {
        viewPagerController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        viewPagerController.clear()
    }

    fun getViewPagerAdapter(fragmentManager: FragmentManager, tags: Set<Tag>): PagerAdapter {
        val postsRepository =
            CachedRepository<Booru.PostRequest, Posts>(Cache.create(12), PostsRepository(booru))
        val previewsRepository =
            CachedRepository<String, ByteArray>(Cache.create(36), PreviewImageRepository(booru))
        val samplesRepository = SampleImageRepository(booru, Cache.create(3))
        return ViewPagerAdapter(fragmentManager, booru, postsRepository, previewsRepository, samplesRepository, tags)
    }

    class Factory(
        private val booru: Booru,
        private val tags: Set<Tag>,
        private val drawerController: DrawerController
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostsFragmentViewModel()

            viewModel.booru = booru
            viewModel.tags = tags
            viewModel.drawerController = drawerController
            viewModel.viewPagerController = ViewPagerController(0)

            return viewModel as T
        }
    }
}