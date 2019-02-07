package com.makentoshe.booruchan.postpreview

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.postpreview.model.GridViewAdapter
import com.makentoshe.booruchan.postpreview.model.PostsDownloadController
import com.makentoshe.booruchan.postpreview.model.PreviewImageDownloadController
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.viewmodel.ViewModel

class PostPageFragmentViewModel private constructor() : ViewModel() {

    private lateinit var booru: Booru
    private var position: Int = -1
    private lateinit var postsRepository: PostsRepository
    private lateinit var postsDownloadController: PostsDownloadController
    private lateinit var previewsImageDownloadController: PreviewImageDownloadController

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
        println("Sas")
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

            viewModel.loadPosts()

            return viewModel as T
        }
    }
}

