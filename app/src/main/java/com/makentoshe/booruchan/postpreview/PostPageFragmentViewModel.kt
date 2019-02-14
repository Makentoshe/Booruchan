package com.makentoshe.booruchan.postpreview

import android.os.Handler
import android.os.Looper
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.PostsDownloadRxController
import com.makentoshe.booruchan.postpreview.model.GridViewAdapter
import com.makentoshe.controllers.DownloadResult
import com.makentoshe.repository.Repository
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router

class PostPageFragmentViewModel private constructor() : ViewModel() {
    private lateinit var router: Router
    private lateinit var booru: Booru
    private lateinit var postsRepository: Repository<Booru.PostRequest, Posts>
    private lateinit var postsDownloadRxController: PostsDownloadRxController
    private lateinit var sampleImageRepository: Repository<String, ByteArray>
    private lateinit var previewsRepository: Repository<String, ByteArray>
    private val disposables = CompositeDisposable()

    fun getGridAdapter(posts: Posts): BaseAdapter {
        return GridViewAdapter(posts, previewsRepository, this, disposables)
    }

    fun navigateToPostDetailsScreen(itemPosition: Int) {
//        val position = itemPosition + this.position * postsRepository.count
//        router.navigateTo(PostSamplesScreen(position, postsRepository, sampleImageRepository))
    }

    fun onGridElementLongClick(position: Int): Boolean {
        println("Long click on $position")
        return true
    }

    override fun onCreateView(owner: Fragment) {
        disposables.clear()
        postsDownloadRxController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        postsDownloadRxController.clear()
    }

    class Factory(
        private val booru: Booru,
        private val postsRepository: Repository<Booru.PostRequest, Posts>,
        private val previewsRepository: Repository<String, ByteArray>,
        private val sampleImageRepository: Repository<String, ByteArray>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostPageFragmentViewModel()
            val postsDownloadRxController = PostsDownloadRxController(viewModel, postsRepository)

            viewModel.previewsRepository = previewsRepository
            viewModel.booru = booru
            viewModel.postsRepository = postsRepository
            viewModel.postsDownloadRxController = postsDownloadRxController
            viewModel.router = Booruchan.INSTANCE.router
            viewModel.sampleImageRepository = sampleImageRepository

            return viewModel as T
        }
    }
}

interface PostsDownloadController {

    val position: Int

    fun loadPosts(page: Int)

    fun addOnPostsReceiveListener(action: (DownloadResult<Posts>) -> Unit)
}

class PostsDownloadViewModel : ViewModel(), PostsDownloadController {

    private lateinit var postsDownloadRxController: PostsDownloadRxController
    private lateinit var tags: Set<Tag>
    private var _position: Int = -1

    override val position: Int
        get() = _position

    override fun loadPosts(page: Int) =
        postsDownloadRxController.action(Booru.PostRequest(12, page, tags))

    override fun addOnPostsReceiveListener(action: (DownloadResult<Posts>) -> Unit) {
        postsDownloadRxController.subscribe { Handler(Looper.getMainLooper()).post { action(it) } }
    }

    override fun onCreateView(owner: Fragment) {
        postsDownloadRxController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        postsDownloadRxController.clear()
    }

    class Factory(
        private val postsRepository: Repository<Booru.PostRequest, Posts>,
        private val tags: Set<Tag>,
        private val position: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostsDownloadViewModel()

            viewModel.postsDownloadRxController = PostsDownloadRxController(viewModel, postsRepository)
            viewModel.tags = tags
            viewModel._position = position

            viewModel.loadPosts(position)

            return viewModel as T
        }
    }
}