package com.makentoshe.booruchan.postpreviewspage

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postpreviewspage.model.GridViewAdapter
import com.makentoshe.booruchan.postpreviewspage.model.PostsDownloadController
import com.makentoshe.repository.cache.CacheImpl

class PostPageFragmentViewModel(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: PostsRepository,
    previewsRepository: PreviewImageRepository
) : FragmentViewModel() {

    private val previewImageDownloadController = PreviewImageDownloadController(this, previewsRepository)
    private val postsDownloadController =
        PostsDownloadController(this, postsRepository)

    init {
        loadPosts()
    }

    fun loadPosts() = postsDownloadController.action(position)

    fun addOnPostsReceiveListener(action: (DownloadResult<Posts>) -> Unit) {
        postsDownloadController.subscribe {
            Handler(Looper.getMainLooper()).post { action(it) }
        }
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

    override fun onUiRecreate() {
        postsDownloadController.clear()
    }

    override fun onCleared() {
        postsDownloadController.clear()
        super.onCleared()
    }
}

