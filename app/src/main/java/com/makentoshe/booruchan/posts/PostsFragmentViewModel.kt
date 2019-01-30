package com.makentoshe.booruchan.posts

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentViewModel
import com.makentoshe.booruchan.PostsRepository
import com.makentoshe.booruchan.PreviewImageRepository
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.booru.model.DrawerState
import com.makentoshe.booruchan.posts.model.*
import com.makentoshe.repository.cache.CacheImpl

class PostsFragmentViewModel(
    val booru: Booru,
    private val drawerController: DrawerController,
    private val tags: Set<Tag>
) : FragmentViewModel() {

    private val clearIconController = ClearIconController()

    private val overflowController = OverflowController(this)

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
        overflowController.update()
        clearIconController.clear()
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
        overflowController.clear()
        clearIconController.clear()
        drawerController.clear()
        selectedTagSetController.clear()
    }

    fun getViewPagerAdapter(fragmentManager: FragmentManager, tags: Set<Tag>): PagerAdapter {
        val postsCountInRequest = 12
        val postsRepository = PostsRepository(booru, CacheImpl(12), postsCountInRequest, tags)
        val previewsRepository = PreviewImageRepository(booru, CacheImpl(postsCountInRequest * 5))
        return ViewPagerAdapter(fragmentManager, booru, postsRepository, previewsRepository)
    }

    fun addOnClearIconClickListener(action: () -> Unit) {
        clearIconController.subscribe { action() }
    }

    fun clickClearIcon() = clearIconController.click()

    fun clickDrawerMenuIcon() {
        if (drawerController.state == null) return drawerController.openDrawer()
        when (drawerController.state) {
            is DrawerState.DrawerOpen -> drawerController.closeDrawer()
            is DrawerState.DrawerClose -> drawerController.openDrawer()
        }
    }

    fun addOnOverflowStateChangedListener(action: OverflowController.OverflowListener.() -> Unit) {
        overflowController.subscribe(action)
    }

    fun clickOverflowIcon() {
        if (overflowController.state == null) {
            overflowController.newState(OverflowController.OverflowState.Cross)
            return
        }
        when (overflowController.state) {
            is OverflowController.OverflowState.Magnify ->
                overflowController.newState(OverflowController.OverflowState.Cross)
            is OverflowController.OverflowState.Cross ->
                overflowController.newState(OverflowController.OverflowState.Magnify)
            else -> Unit
        }
    }

    val overflowState: OverflowController.OverflowState?
        get() = overflowController.state
}