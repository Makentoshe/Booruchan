package com.makentoshe.booruchan.postpreview

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.PostsDownloadRxController
import com.makentoshe.booruchan.postpreview.model.GridViewAdapter
import com.makentoshe.booruchan.postpreview.model.PreviewImageDownloadController
import com.makentoshe.booruchan.postsamples.PostSamplesScreen
import com.makentoshe.controllers.DownloadResult
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.viewmodel.ViewModel
import ru.terrakok.cicerone.Router

class PostPageFragmentViewModel private constructor() : ViewModel() {
    private lateinit var router: Router
    private lateinit var booru: Booru
    private var position: Int = -1
    private lateinit var postsRepository: PostsRepository
    private lateinit var postsDownloadRxController: PostsDownloadRxController
    private lateinit var previewsImageDownloadController: PreviewImageDownloadController
    private lateinit var sampleImageRepository: SampleImageRepository

    fun loadPosts() = postsDownloadRxController.action(position)

    fun addOnPostsReceiveListener(action: (DownloadResult<Posts>) -> Unit) {
        postsDownloadRxController.subscribe { Handler(Looper.getMainLooper()).post { action(it) } }
    }

    fun loadPreviews(posts: Posts) = posts.forEach {
        previewsImageDownloadController.action(it.previewUrl, this)
    }

    fun getGridAdapter(posts: Posts): BaseAdapter {
        return GridViewAdapter(posts, previewsImageDownloadController)
    }

    fun navigateToPostDetailsScreen(itemPosition: Int) {
        val position = itemPosition + this.position * postsRepository.count
        router.navigateTo(PostSamplesScreen(position, postsRepository, sampleImageRepository))
    }

    fun onGridElementLongClick(position: Int): Boolean {
        println("Long click on $position")
        return true
    }

    override fun onCreateView(owner: Fragment) {
        postsDownloadRxController.clear()
        previewsImageDownloadController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        postsDownloadRxController.clear()
        previewsImageDownloadController.clear()
    }

    class Factory(
        private val booru: Booru,
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val previewsRepository: ImageRepository,
        private val sampleImageRepository: SampleImageRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostPageFragmentViewModel()
            val postsDownloadRxController = PostsDownloadRxController(viewModel, postsRepository)

            viewModel.booru = booru
            viewModel.position = position
            viewModel.postsRepository = postsRepository
            viewModel.postsDownloadRxController = postsDownloadRxController
            viewModel.previewsImageDownloadController = PreviewImageDownloadController(previewsRepository)
            viewModel.router = Booruchan.INSTANCE.router
            viewModel.sampleImageRepository = sampleImageRepository
            viewModel.loadPosts()

            return viewModel as T
        }
    }
}

