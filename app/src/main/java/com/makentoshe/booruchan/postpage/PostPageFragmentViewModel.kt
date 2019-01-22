package com.makentoshe.booruchan.postpage

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.PostSamplesScreen
import com.makentoshe.booruchan.SampleImageRepository
import com.makentoshe.booruchan.postpage.model.GridViewAdapter
import com.makentoshe.booruchan.postpage.model.PostsDownloadController
import com.makentoshe.booruchan.postpage.model.PreviewsDownloadController
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import com.makentoshe.repository.cache.CacheImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostPageFragmentViewModel(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: PostsRepository,
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
        val startPosition = position + this.position * postsRepository.count
        val samplesRepository = SampleImageRepository(booru, CacheImpl(postsRepository.count))
        val screen = PostSamplesScreen(booru, startPosition, postsRepository, samplesRepository)
        Booruchan.INSTANCE.router.navigateTo(screen)
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