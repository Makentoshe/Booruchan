package com.makentoshe.booruchan.posts

import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.booru.DrawerController
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

    val autocompleteRepository = DelayAutocompleteRepository(booru)
    val autocompleteAdapter = DelayAutocompleteAdapter(autocompleteRepository)

    fun update() = launch {
        uiController = UIController(OverflowController(), drawerController)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}