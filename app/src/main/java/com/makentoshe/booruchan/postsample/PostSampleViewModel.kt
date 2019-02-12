package com.makentoshe.booruchan.postsample

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.ByteArrayDownloadRxController
import com.makentoshe.booruchan.PostsDownloadRxController
import com.makentoshe.booruchan.postsample.model.DownloadErrorController
import com.makentoshe.booruchan.postsample.model.DownloadingCompleteRxController
import com.makentoshe.booruchan.postsample.model.SampleDownloadController
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.viewmodel.ViewModel

class PostSampleViewModel private constructor() : ViewModel() {
    /* All posts start from 0 in the search. This position is a number of a post from the search.
     * It includes the page number and item number.
     * The expression is: position = pagePosition * itemsCountPerPage + itemPosition */
    var position = 0
        private set

    /* Position of the post in page from 0 to itemsCountPerPage */
    var itemPosition = 0
        private set

    /* Position of the posts in the search result.*/
    var pagePosition = 0
        private set

    /* Performs posts downloading */
    private lateinit var postsDownloadRxController: PostsDownloadRxController

    /* Performs sample image downoading */
    private lateinit var samplesDownloadController: ByteArrayDownloadRxController<String>

    /* When download error occurs */
    private lateinit var downloadErrorController: DownloadErrorController

    private lateinit var downloadingCompleteRxController: DownloadingCompleteRxController

    /* Starts post loading for selected page in another thread.
    * When posts will be loaded the sample image downloading will be started automatically.
    * The result of the image downloading will be send to the onSampleDownloadedListener
    * when downloading is success or onDownloadingErrorListener otherwise.*/
    fun loadPosts(page: Int) = postsDownloadRxController.action(page)

    /**
     * Listener for the downloading complete success event.
     * If the byte array represents image or a gif animation
     * the byte array will be contains it as is. But if the byte
     * array represents a WebM file the byte array will be contains
     * a url string.
     * <p>
     * If an error occur the event was not invoked, but the
     * [onDownloadingErrorListener] will be.
     */
    fun onSampleDownloadedListener(action: (ByteArray) -> Unit) {
        //subscribe for receiving a posts
        postsDownloadRxController.subscribe {
            if (it.hasData()) {
//                send a selected post to the sample download controller
                samplesDownloadController.action(it.data[itemPosition].sampleUrl)
            } else {
                if (it.hasException()) {
                    downloadErrorController.action(it.exception)
                } else {
                    downloadErrorController.action(Exception("Exception while post download"))
                }
            }
        }
//        also subscribes for samples receiving
        samplesDownloadController.subscribe {
            if (it.hasData()) {
                Handler(Looper.getMainLooper()).post {
                    action(it.data)
                    downloadingCompleteRxController.action(Unit)
                }
            } else {
                downloadErrorController.action(it.exception)
            }
        }
    }

    /* Calls when downloading was finished. The downloading result does not interest here. */
    fun onDownloadingCompleteListener(action: () -> Unit) {
        downloadingCompleteRxController.subscribe { Handler(Looper.getMainLooper()).post(action) }
    }

    /* Calls when error occurs while sample image or posts downloading*/
    fun onDownloadingErrorListener(action: (Exception) -> Unit) {
        downloadErrorController.subscribe {
            Handler(Looper.getMainLooper()).post {
                action(it)
                downloadingCompleteRxController.action(Unit)
            }
        }
    }

    /* Shows a message with the exception */
    fun pushException(exception: Exception) = downloadErrorController.action(exception)

    override fun onCreateView(owner: Fragment) {
        samplesDownloadController.clear()
        postsDownloadRxController.clear()
        downloadErrorController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        samplesDownloadController.clear()
        downloadErrorController.clear()
        postsDownloadRxController.clear()
    }

    class Factory(
        private val position: Int,
        private val postsRepository: PostsRepository,
        private val samplesRepository: SampleImageRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSampleViewModel()
//            val postsDownloadRxController = PostsDownloadRxController(viewModel, postsRepository)
            val samplesDownloadRxController =
                SampleDownloadController(viewModel, samplesRepository)
            val downloadingCompleteRxController = DownloadingCompleteRxController()

            viewModel.position = position
//            viewModel.itemPosition = position % postsRepository.count
//            viewModel.pagePosition = position / postsRepository.count
//            viewModel.downloadErrorController = DownloadErrorController()
//            viewModel.postsDownloadRxController = postsDownloadRxController
            viewModel.samplesDownloadController = samplesDownloadRxController
            viewModel.downloadingCompleteRxController = downloadingCompleteRxController

            viewModel.loadPosts(viewModel.pagePosition)

            return viewModel as T
        }
    }
}

