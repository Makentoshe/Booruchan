package com.makentoshe.booruchan.postpreviewspage

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postpreviewspage.model.GridViewAdapter
import com.makentoshe.booruchan.postpreviewspage.model.PostsDownloadController
import com.makentoshe.booruchan.postpreviewspage.model.PreviewImageDownloadController
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.PreviewImageRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.repository.cache.Cache

class PostPageFragmentViewModel(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: PostsRepository,
    previewsRepository: PreviewImageRepository
) : FragmentViewModel() {

    private val postsDownloadController = PostsDownloadController(this, postsRepository)
    private val previewsImageDownloadController =
        PreviewImageDownloadController(previewsRepository)

    init {
        loadPosts()
    }

    fun loadPosts() = postsDownloadController.action(position)

    fun addOnPostsReceiveListener(action: (DownloadResult<Posts>) -> Unit) {
        postsDownloadController.subscribe { Handler(Looper.getMainLooper()).post { action(it) } }
    }

    fun loadPreviews(posts: Posts) = posts.forEach {
        previewsImageDownloadController.action(it.previewUrl, this)
    }

    fun getGridAdapter(posts: Posts): BaseAdapter {
        return GridViewAdapter(posts, previewsImageDownloadController)
    }

    fun navigateToPostDetailsScreen(position: Int) {
        val startPosition = position + this.position * postsRepository.count
        val samplesRepository = SampleImageRepository(booru, Cache.create(postsRepository.count))
        val screen = PostSamplesScreen(booru, startPosition, postsRepository, samplesRepository)
        Booruchan.INSTANCE.router.navigateTo(screen)
    }

    fun onGridElementLongClick(position: Int): Boolean {
        println("Long click on $position")
        return true
    }

    override fun onCreateView(owner: Fragment) {
        postsDownloadController.clear()
        previewsImageDownloadController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        postsDownloadController.clear()
        previewsImageDownloadController.clear()
    }
}

