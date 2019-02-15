package com.makentoshe.booruchan.postpreview

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.PostsDownloadRxController
import com.makentoshe.booruchan.postpreview.model.PostsDownloadController
import com.makentoshe.controllers.DownloadResult
import com.makentoshe.repository.Repository
import com.makentoshe.viewmodel.ViewModel

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

            viewModel.postsDownloadRxController =
                    PostsDownloadRxController(viewModel, postsRepository)
            viewModel.tags = tags
            viewModel._position = position

            viewModel.loadPosts(position)

            return viewModel as T
        }
    }
}