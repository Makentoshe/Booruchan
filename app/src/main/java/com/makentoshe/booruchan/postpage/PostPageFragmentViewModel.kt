package com.makentoshe.booruchan.postpage

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.PostSamplesScreen
import com.makentoshe.booruchan.StartScreen
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
    previewsRepository: PreviewsRepository
) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val postsDownloadController = PostsDownloadController(postsRepository)

    private val previewDownloadController = PreviewsDownloadController(this, previewsRepository)

    init {
        launch {
            try {
                postsDownloadController.loadPosts(position)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun subscribeOnPosts(action: (Posts) -> Unit) = postsDownloadController.subscribe {
        Handler(Looper.getMainLooper()).post { action(it) }
    }

    fun getGridAdapter(posts: Posts): BaseAdapter {
        return GridViewAdapter(posts, previewDownloadController)
    }

    fun navigateToPostDetailsScreen(position: Int) {
        println("$position\t${this.position}")
        Booruchan.INSTANCE.router.replaceScreen(PostSamplesScreen(booru))
    }

    fun onGridElementLongClick(position: Int): Boolean {
        println("Long click on $position")
        return true
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