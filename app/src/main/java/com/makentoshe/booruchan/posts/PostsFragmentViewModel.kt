package com.makentoshe.booruchan.posts

import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.booru.DrawerController
import com.makentoshe.booruchan.posts.model.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostsFragmentViewModel(
    val booru: Booru,
    private val drawerController: DrawerController
) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    lateinit var uiController: UIController
        private set

    lateinit var selectedTagSetController: SelectedTagSetController
        private set

    private lateinit var searchController: SearchController

    private val autocompleteRepository = DelayAutocompleteRepository(booru)

    val autocompleteAdapter: DelayAutocompleteAdapter
        get() = DelayAutocompleteAdapter(autocompleteRepository)

    fun startNewSearch() {
        searchController.newSearch(selectedTagSetController.tags)
    }

    fun onNewSearchStarted(action: (Set<Tag>) -> Unit) {
        searchController.subscribe(action)
    }

    fun update() {
        uiController = UIController(OverflowController(), drawerController, ClearIconController())
        searchControllerUpdate()
        selectedTagSetControllerUpdate()
    }

    private fun searchControllerUpdate() {
        searchController = if (!::searchController.isInitialized) {
            SearchController().apply { newSearch(setOf()) }
        } else {
            SearchController()
        }
    }

    private fun selectedTagSetControllerUpdate() {
        selectedTagSetController = if (::selectedTagSetController.isInitialized) {
            SelectedTagSetController(selectedTagSetController.tags)
        } else {
            SelectedTagSetController(setOf())
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}