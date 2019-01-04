package com.makentoshe.booruchan.postpage

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpage.model.PostsDownloadController
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostPageFragmentViewModel(
    private val booru: Booru,
    private val position: Int,
    postsRepository: PostsRepository,
    previewsRepository: PreviewsRepository
) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val postsDownloadController = PostsDownloadController(postsRepository)

    init {
        launch {
            withTimeout(5000) {
                postsDownloadController.loadPosts(position)
            }
        }
    }

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

    fun update() {
        postsDownloadController.update()
    }
}