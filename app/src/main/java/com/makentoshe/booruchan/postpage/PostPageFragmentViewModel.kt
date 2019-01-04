package com.makentoshe.booruchan.postpage

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpage.model.GridViewAdapter
import com.makentoshe.booruchan.postpage.model.PostsDownloadController
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostPageFragmentViewModel(
    private val position: Int,
    postsRepository: PostsRepository,
    private val previewsRepository: PreviewsRepository
) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val postsDownloadController = PostsDownloadController(postsRepository)

    init {
        launch {
            postsDownloadController.loadPosts(position)
        }
    }

    fun subscribeOnPosts(action: (Posts) -> Unit) {
        postsDownloadController.subscribe {
            Handler(Looper.getMainLooper()).post { action(it) }
        }
    }

    val posts = postsDownloadController.value

    fun getGridAdapter(posts: Posts): BaseAdapter {
        return GridViewAdapter(posts)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun update() {
        postsDownloadController.update()
    }
}