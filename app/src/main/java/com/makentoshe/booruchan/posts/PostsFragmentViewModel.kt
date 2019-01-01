package com.makentoshe.booruchan.posts

import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostsFragmentViewModel(val booru: Booru) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    lateinit var uiController: UIController
        private set

    fun update() = launch {
        uiController = UIController(OverflowController())
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}