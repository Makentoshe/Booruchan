package com.makentoshe.booruchan.postpage

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.postpage.model.GridViewAdapter
import com.makentoshe.booruchan.postpage.model.PostsDownloadController
import com.makentoshe.booruchan.postpage.model.PreviewsDownloadController
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class PostPageFragmentViewModel(
    private val booru: Booru,
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
            try {
                withTimeout(5000) {
                    postsDownloadController.loadPosts(position)
                }
            } catch (e: Exception) {
            }
        }
    }

    fun subscribeOnPosts(action: (Posts) -> Unit) {
        postsDownloadController.subscribe {
            Handler(Looper.getMainLooper()).post { action(it) }
        }
    }

    val posts = postsDownloadController.value



    private lateinit var previewsDownloadController: PreviewsDownloadController

    fun getGridAdapter(posts: Posts): BaseAdapter {
        startLoadPreviews(posts)
        return GridViewAdapter(posts, getPreviewsDownloadController())
    }

    private fun startLoadPreviews(posts: Posts) {
        (0 until posts.size).forEach {
            launch {
                try {
                    withTimeout(5000) {
                        getPreviewsDownloadController().loadPreview(it, posts[it].previewUrl)
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun update() {
        postsDownloadController.update()
    }

    private fun getPreviewsDownloadController(): PreviewsDownloadController {
        return if (::previewsDownloadController.isInitialized) previewsDownloadController
        else PreviewsDownloadController(previewsRepository).apply {
            previewsDownloadController = this
        }
    }
}