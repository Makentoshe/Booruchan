package com.makentoshe.booruchan.postsample

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.Controller
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.postpreview.model.PostsDownloadController
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.viewmodel.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class PostSampleViewModel private constructor() : ViewModel() {
    private var position = 0
    private lateinit var postsDownloadController: PostsDownloadController
    private lateinit var samplesRepository: SampleImageRepository

//    fun loadPosts(page: Int) = postsDownloadController.action(page)
//
//    fun onPostsDownloadedListener(action: (DownloadResult<Posts>) -> Unit) {
//        postsDownloadController.subscribe {
//            Handler(Looper.getMainLooper()).post { action(it) }
//        }
//    }
//
//    fun onSampleDownloadedListener(action: (DownloadResult<ByteArray>) -> Unit) {
//        postsDownloadController.subscribe {
//
//        }
//    }

    override fun onCreateView(owner: Fragment) {
        postsDownloadController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        postsDownloadController.clear()
    }

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val samplesRepository: SampleImageRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSampleViewModel()
            viewModel.position = position
            viewModel.postsDownloadController = PostsDownloadController(viewModel, postsRepository)
            viewModel.samplesRepository = samplesRepository
            return viewModel as T
        }
    }
}


/**
 * Class for performing preview image download.
 */
class SampleImageDownloadController(
    private val coroutineScope: CoroutineScope,
    private val previewsRepository: ImageRepository
) : Controller<DownloadResult<Pair<String, ByteArray>>> {
    private val observable = ReplaySubject.create<DownloadResult<Pair<String, ByteArray>>>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (DownloadResult<Pair<String, ByteArray>>) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    //TODO Memory leak in coroutines
    //PreviewImageDownloadController instance can't be GC
    fun action(post: Post) = coroutineScope.launch {
        try {
            observable.onNext(DownloadResult(Pair(post.sampleUrl, previewsRepository.get(post.sampleUrl)!!)))
        } catch (e: Exception) {
            observable.onNext(DownloadResult(exception = e))
        }
    }

    override fun clear() {
        disposables.clear()
        observable.cleanupBuffer()
    }
}