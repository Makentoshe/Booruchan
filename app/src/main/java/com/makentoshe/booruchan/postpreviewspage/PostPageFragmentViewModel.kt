package com.makentoshe.booruchan.postpreviewspage

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.PostSamplesScreen
import com.makentoshe.booruchan.postpreviewspage.model.GridViewAdapter
import com.makentoshe.booruchan.postpreviewspage.model.PostsDownloadController
import com.makentoshe.booruchan.postpreviewspage.model.PreviewImageDownloadController
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.repository.cache.Cache
import com.makentoshe.viewmodel.ViewModel

class PostPageFragmentViewModel private constructor() : ViewModel() {

    private lateinit var booru: Booru
    private var position: Int = -1
    private lateinit var postsRepository: PostsRepository
    private lateinit var postsDownloadController: PostsDownloadController
    private lateinit var previewsImageDownloadController: PreviewImageDownloadController

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

    class Factory(
        private val booru: Booru,
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val previewsRepository: ImageRepository
    ): ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostPageFragmentViewModel()
            viewModel.booru = booru
            viewModel.position = position
            viewModel.postsRepository = postsRepository
            viewModel.postsDownloadController = PostsDownloadController(viewModel, postsRepository)
            viewModel.previewsImageDownloadController = PreviewImageDownloadController(previewsRepository)
            return viewModel as T
        }
    }
}

