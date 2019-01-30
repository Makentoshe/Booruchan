package com.makentoshe.booruchan.posts

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentViewModel
import com.makentoshe.booruchan.PostsRepository
import com.makentoshe.booruchan.PreviewImageRepository
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.posts.model.*
import com.makentoshe.repository.cache.CacheImpl

class PostsFragmentViewModel(
    val booru: Booru,
    private val drawerController: DrawerController,
    private val tags: Set<Tag>
) : FragmentViewModel() {

    private val clearIconControler = ClearIconController()

    lateinit var uiController: UIController
        private set

    lateinit var selectedTagSetController: SelectedTagSetController
        private set

    private lateinit var searchController: SearchController

    lateinit var viewPagerController: ViewPagerController


    val autocompleteAdapter: DelayAutocompleteAdapter
        get() = DelayAutocompleteAdapter(DelayAutocompleteRepository(booru))

    fun startNewSearch() {
        searchController.newSearch(selectedTagSetController.tags)
    }

    fun onNewSearchStarted(action: (Set<Tag>) -> Unit) {
        searchController.subscribe(action)
    }

    override fun onUiRecreate() {
        clearIconControler.clear()
        uiController = UIController(OverflowController(), drawerController)
        searchControllerUpdate()
        selectedTagSetControllerUpdate()
        viewPagerControllerUpdate()
    }

    private fun searchControllerUpdate() {
        searchController = if (::searchController.isInitialized && searchController.value != null) {
            val value = searchController.value
            SearchController().apply { newSearch(value!!) }
        } else {
            SearchController().apply { newSearch(tags) }
        }
    }

    private fun selectedTagSetControllerUpdate() {
        selectedTagSetController = if (::selectedTagSetController.isInitialized) {
            selectedTagSetController.clear()
            SelectedTagSetController(selectedTagSetController.tags)
        } else {
            SelectedTagSetController(setOf())
        }
    }

    private fun viewPagerControllerUpdate() {
        viewPagerController = if (::viewPagerController.isInitialized) {
            ViewPagerController(viewPagerController.value)
        } else {
            ViewPagerController(0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearIconControler.clear()
        selectedTagSetController.clear()
    }

    fun getViewPagerAdapter(fragmentManager: FragmentManager, tags: Set<Tag>): PagerAdapter {
        val postsCountInRequest = 12
        val postsRepository = PostsRepository(booru, CacheImpl(12), postsCountInRequest, tags)
        val previewsRepository = PreviewImageRepository(booru, CacheImpl(postsCountInRequest * 5))
        return ViewPagerAdapter(fragmentManager, booru, postsRepository, previewsRepository)
    }

    fun addOnClearIconClickListener(action: () -> Unit) {
        clearIconControler.subscribe { action() }
    }

    fun clickClearIcon() = clearIconControler.click()
}