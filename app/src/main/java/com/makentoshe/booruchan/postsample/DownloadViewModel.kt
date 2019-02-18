package com.makentoshe.booruchan.postsample

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.ByteArrayDownloadRxController
import com.makentoshe.booruchan.PostsDownloadRxController
import com.makentoshe.booruchan.postsample.model.DownloadErrorController
import com.makentoshe.booruchan.postsample.model.ExceptionRxController
import com.makentoshe.booruchan.postsample.model.SampleImageDownloadController
import com.makentoshe.controllers.SimpleRxController
import com.makentoshe.repository.Repository
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.subjects.BehaviorSubject
import java.io.File

class DownloadViewModel private constructor() : ViewModel(), SampleImageDownloadController, DownloadErrorController {

    private lateinit var exceptionRxController: ExceptionRxController
    private lateinit var imageDownloadRxController: ByteArrayDownloadRxController<Post>
    private lateinit var postDownloadRxController: PostsDownloadRxController
    private lateinit var stringRxController: StringRxController
    private lateinit var tags: Set<Tag>

    private fun loadPost(position: Int) = loadPost(Booru.PostRequest(1, position, tags))

    private fun loadPost(request: Booru.PostRequest) {
        postDownloadRxController.action(request)
    }

    private fun onPostLoadedListener(action: (Posts) -> Unit) {
        postDownloadRxController.subscribe {
            if (it.hasData()) {
                Handler(Looper.getMainLooper()).post { action(it.data) }
            } else {
                exceptionRxController.action(it.exception)
            }
        }
    }

    override fun push(exception: java.lang.Exception) = exceptionRxController.action(exception)

    override fun loadSampleImage(post: Post) = imageDownloadRxController.action(post)

    override fun passUrlToTheFile(url: String) = stringRxController.action(url)

    override fun onSampleImageLoaded(action: (ByteArray) -> Unit) {
        imageDownloadRxController.subscribe {
            if (it.hasData()) {
                Handler(Looper.getMainLooper()).post { action(it.data) }
            } else {
                exceptionRxController.action(it.exception)
            }
        }
        stringRxController.subscribe {
            Handler(Looper.getMainLooper()).post { action(it.toByteArray()) }
        }
    }

    override fun onSampleImageLoadingError(action: (Exception) -> Unit) {
        exceptionRxController.subscribe { Handler(Looper.getMainLooper()).post { action(it) } }
    }

    override fun onCreateView(owner: Fragment) {
        imageDownloadRxController.clear()
        stringRxController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        stringRxController.clear()
        postDownloadRxController.clear()
        imageDownloadRxController.clear()
    }

    class Factory(
        private val postsRepository: Repository<Booru.PostRequest, Posts>,
        private val tags: Set<Tag>,
        private val samplesRepository: Repository<Post, ByteArray>,
        private val position: Int
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = DownloadViewModel()

            viewModel.imageDownloadRxController = ByteArrayDownloadRxController(viewModel, samplesRepository)
            viewModel.postDownloadRxController = PostsDownloadRxController(viewModel, postsRepository)
            viewModel.exceptionRxController = ExceptionRxController()
            viewModel.stringRxController = StringRxController()
            viewModel.tags = tags

            viewModel.loadPost(position)
            viewModel.onPostLoadedListener {
                val post = it[0]
                if (File(post.fileUrl).extension == "webm") {
                    viewModel.stringRxController.action(post.fileUrl)
                } else {
                    viewModel.loadSampleImage(it[0])
                }
            }

            return viewModel as T
        }
    }
}

class StringRxController : SimpleRxController<String, String>(BehaviorSubject.create()) {
    override fun action(param: String) = observable.onNext(param)
}