package com.makentoshe.booruchan.postpage

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpage.model.PostsDownloadController
import com.makentoshe.booruchan.posts.model.PostsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostPageFragmentViewModel(
    private val booru: Booru,
    private val position: Int,
    postsRepository: PostsRepository
) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val postsDownloadController = PostsDownloadController(postsRepository)

    init {
        launch {
            withTimeout(5000) {
                loadPosts()
            }
        }
    }

    private fun loadPosts() = postsDownloadController.loadPosts(position)

    fun subscribeOnPosts(action: (Posts) -> Unit) {
        postsDownloadController.subscribe {
            Handler(Looper.getMainLooper()).post { action(it) }
        }
    }

    val posts = postsDownloadController.value

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

