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
    /* tags for default(on the startup) search */
    private val tags: Set<Tag>
) : FragmentViewModel() {

    private val clearIconController = ClearIconController()
    private val overflowController = OverflowController(this)
    private val searchController = SearchController()
    private val compositeTagController = CompositeTagController(TagController(), TagController(), tags)

    fun clickDrawerMenuIcon() {
        if (drawerController.state == null) return drawerController.openDrawer()
        when (drawerController.state) {
            is DrawerState.DrawerOpen -> drawerController.closeDrawer()
            is DrawerState.DrawerClose -> drawerController.openDrawer()
        }
    }

    fun clickClearIcon() = clearIconController.click()

    fun addOnClearIconClickListener(action: () -> Unit) {
        clearIconController.subscribe { action() }
    }

    val overflowState: OverflowController.OverflowState?
        get() = overflowController.state

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

    fun addOnOverflowStateChangedListener(action: OverflowController.OverflowListener.() -> Unit) {
        overflowController.subscribe(action)
    }

    fun startNewSearch() = searchController.newSearch(compositeTagSet)

    fun onSearchStartedListener(action: (Set<Tag>) -> Unit) = searchController.subscribe(action)

    fun onAddTagSubscribe(action: (Tag) -> Unit) = compositeTagController.subscribeOnAdd(action)

    fun onRemTagSubscribe(action: (Tag) -> Unit) = compositeTagController.subscribeOnRemove(action)

    fun addTag(tag: Tag) = compositeTagController.addTag(tag)

    fun removeTag(tag: Tag) = compositeTagController.removeTag(tag)

    val compositeTagSet: Set<Tag>
        get() = compositeTagController.tagSet


    lateinit var viewPagerController: ViewPagerController


    val autocompleteAdapter: DelayAutocompleteAdapter
        get() = DelayAutocompleteAdapter(DelayAutocompleteRepository(booru))

    override fun onUiRecreate() {
        overflowController.update()
        clearIconController.clear()
        searchController.update(tags)
        compositeTagController.clear()
        viewPagerControllerUpdate()
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
        compositeTagController.clear()
    }

    fun getViewPagerAdapter(fragmentManager: FragmentManager, tags: Set<Tag>): PagerAdapter {
        val postsCountInRequest = 12
        val postsRepository = PostsRepository(booru, CacheImpl(12), postsCountInRequest, tags)
        val previewsRepository = PreviewImageRepository(booru, CacheImpl(postsCountInRequest * 5))
        return ViewPagerAdapter(fragmentManager, booru, postsRepository, previewsRepository)
    }

}