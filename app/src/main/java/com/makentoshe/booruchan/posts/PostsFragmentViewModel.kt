package com.makentoshe.booruchan.posts

import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
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

    private val autocompleteRepository = DelayAutocompleteRepository(booru)
    val autocompleteAdapter = DelayAutocompleteAdapter(autocompleteRepository)

    fun update() {
        uiController = UIController(OverflowController(), drawerController, ClearIconController())
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}