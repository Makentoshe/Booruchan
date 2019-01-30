package com.makentoshe.booruchan.postpage

import android.widget.BaseAdapter
import androidx.lifecycle.ViewModel
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postpage.model.GridViewAdapter
import com.makentoshe.repository.cache.CacheImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class PostPageFragmentViewModel(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: PostsRepository,
    previewsRepository: PreviewImageRepository
) : ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private val postsDownloadController = PostsDownloadController(this, postsRepository)

    private val previewImageDownloadController = PreviewImageDownloadController(this, previewsRepository)

    fun subscribeOnPosts(action: (DownloadResult<Posts>) -> Unit) {
        postsDownloadController.subscribe(DownloadResult(position), action)
    }

    fun getGridAdapter(posts: Posts): BaseAdapter {
        return GridViewAdapter(posts, previewImageDownloadController)
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

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}