package com.makentoshe.booruchan.postpage

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpage.model.GridViewAdapter
import com.makentoshe.booruchan.postpage.model.PostsDownloadController
import com.makentoshe.booruchan.postpage.model.PreviewsDownloadController
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostPageFragmentViewModel(
    position: Int,
    postsRepository: PostsRepository,
    previewsRepository: PreviewsRepository
) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val postsDownloadController = PostsDownloadController(postsRepository)

    private val previewDownloadController = PreviewsDownloadController(this, previewsRepository)

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


    fun getGridAdapter(posts: Posts): BaseAdapter {
        return GridViewAdapter(posts, previewDownloadController)
    }

    fun update() {
        postsDownloadController.update()
    }

    override fun onCleared() {
        postsDownloadController.update()
        super.onCleared()
        job.cancel()
    }

}