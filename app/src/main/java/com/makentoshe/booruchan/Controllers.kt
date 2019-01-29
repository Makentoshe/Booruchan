package com.makentoshe.booruchan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruapi.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Default controller interface.
 */
interface Controller<P, T> {
    /**
     * Method subscribes on [T] receive.
     * Pass [P] as a param and you will be subscribed on return [T] in lambda.
     */
    fun subscribe(param: P, action: (T) -> Unit)
}


/**
 * Any download will be wrapped in this class.
 */
data class DownloadResult<T>(val data: T? = null, val exception: Exception? = null)

/**
 * Class for performing download.
 */
abstract class DownloadController<P, T>(
    protected val coroutineScope: CoroutineScope
) : Controller<DownloadResult<P>, DownloadResult<T>>

/**
 * Class for performing sample image download using [Post] instance.
 */
class ImageDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: ImageRepository
) : DownloadController<Post, Bitmap>(coroutineScope) {

    /**
     * Download and push result into lambda.
     */
    override fun subscribe(downloadRequest: DownloadResult<Post>, action: (DownloadResult<Bitmap>) -> Unit) {
        if (downloadRequest.data == null) {
            action.pushResult(DownloadResult(null, downloadRequest.exception))
        } else {
            coroutineScope.launch {
                try {
                    action.pushResult(DownloadResult(performDownload(downloadRequest)))
                } catch (e: Exception) {
                    action.pushResult(DownloadResult(null, e))
                }
            }
        }
    }

    /**
     * Push the download result into the lambda.
     */
    private fun ((DownloadResult<Bitmap>) -> Unit).pushResult(result: DownloadResult<Bitmap>) {
        Handler(Looper.getMainLooper()).post { this.invoke(result) }
    }

    /**
     * Perform downloading the sample image.
     */
    private fun performDownload(request: DownloadResult<Post>): Bitmap {
        val byteArray = repository.get(request.data!!.sampleUrl)!!
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}