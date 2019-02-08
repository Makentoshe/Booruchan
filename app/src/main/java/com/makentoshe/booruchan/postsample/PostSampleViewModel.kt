package com.makentoshe.booruchan.postsample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
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
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PostSampleViewModel private constructor() : ViewModel() {
    /* All posts start from 0 in current search. This position is a number of a post from the search.
     * it includes the page number and item number.
     * The expression is position = pagePosition * itemsCountPerPage + itemPosition */
    var position = 0
        private set

    /* Position of the post in page from 0 to itemsCountPerPage */
    var itemPosition = 0
        private set

    /* Position of the posts in the search result.*/
    var pagePosition = 0
        private set

    /* Performs posts downloading */
    private lateinit var postsDownloadController: PostsDownloadController

    /* Performs sample image downoading */
    private lateinit var samplesDownloadController: SampleImageDownloadController

    /* When download error occurs */
    private lateinit var downloadErrorController: DownloadErrorController

    /* Starts post loading for selected page in another thread.
    * When posts will be loaded the sample image downloading will be started automatically.
    * The result of the image downloading will be send to the onSampleDownloadedListener
    * when downloading is success or onDownloadingErrorListener otherwise.*/
    fun loadPosts(page: Int) = postsDownloadController.action(page)

    /* Listener for downloading complete successfully event.
    * When errors occurs or any else the event was not invoked,
    * but the onDownloadingErrorListener will be. */
    fun onSampleDownloadedListener(action: (Bitmap) -> Unit) {
        //subscribe for receiving a posts
        postsDownloadController.subscribe {
            if (it.data != null) {
                //send a selected post to the sample download controller
                samplesDownloadController.action(it.data[itemPosition])
            } else {
                downloadErrorController.action(it.exception ?: Exception("Exception while post download"))
            }
        }
        //also subscribes for samples receiving
        samplesDownloadController.subscribe {
            if (it.data != null) {
                //create bitmap and send
                val byteArray = it.data.second
                Handler(Looper.getMainLooper()).post {
                    action(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
                }
            } else {
                downloadErrorController.action(it.exception?: Exception("Exception while image download"))
            }
        }
    }

    /* Calls when error occurs while sample image or posts downloading*/
    fun onDownloadingErrorListener(action: (Exception) -> Unit) {
        downloadErrorController.subscribe {
            Handler(Looper.getMainLooper()).post { action(it) }
        }
    }

    override fun onCreateView(owner: Fragment) {
        postsDownloadController.clear()
        downloadErrorController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        downloadErrorController.clear()
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
            viewModel.itemPosition = position % postsRepository.count
            viewModel.pagePosition = position / postsRepository.count
            viewModel.postsDownloadController = PostsDownloadController(viewModel, postsRepository)
            viewModel.samplesDownloadController = SampleImageDownloadController(viewModel, samplesRepository)
            viewModel.downloadErrorController = DownloadErrorController()

            viewModel.loadPosts(viewModel.pagePosition)

            return viewModel as T
        }
    }
}

class DownloadErrorController : Controller<Exception> {

    private val observable = BehaviorSubject.create<Exception>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (Exception) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(exception: Exception) = observable.onNext(exception)

    override fun clear() = disposables.clear()
}

/**
 * Class for performing preview image download.
 */
class SampleImageDownloadController(
    private val coroutineScope: CoroutineScope,
    private val samplesRepository: ImageRepository
) : Controller<DownloadResult<Pair<String, ByteArray>>> {
    private val observable = ReplaySubject.create<DownloadResult<Pair<String, ByteArray>>>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (DownloadResult<Pair<String, ByteArray>>) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(post: Post) = coroutineScope.launch {
        try {
            observable.onNext(DownloadResult(Pair(post.sampleUrl, samplesRepository.get(post.sampleUrl)!!)))
        } catch (e: Exception) {
            observable.onNext(DownloadResult(exception = e))
        }
    }

    override fun clear() {
        disposables.clear()
        observable.cleanupBuffer()
    }
}