package com.makentoshe.booruchan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Default controller interface.
 */
interface Controller<T> {
    fun subscribe(action: (T) -> Unit)
}

/**
 * Default controller interface.
 */
interface DoubleController<P, T> {
    /**
     * Method subscribes on [T] receive.
     * Do something with [P] and return [T] in lambda.
     */
    fun subscribe(param: P, action: (T) -> Unit)
}

/**
 * Performs download.
 */
abstract class DownloadDoubleController<T, P>(
    protected val coroutineScope: CoroutineScope
) : DoubleController<DownloadResult<T>, DownloadResult<P>> {

    override fun subscribe(param: DownloadResult<T>, action: (DownloadResult<P>) -> Unit) {
        if (param.data == null) {
            action.pushResult(DownloadResult(null, param.exception))
        } else {
            coroutineScope.launch {
                try {
                    action.pushResult(DownloadResult(performDownload(param)))
                } catch (e: Exception) {
                    action.pushResult(DownloadResult(null, e))
                }
            }
        }
    }

    /**
     * Push the download result into the lambda.
     */
    protected fun ((DownloadResult<P>) -> Unit).pushResult(result: DownloadResult<P>) {
        Handler(Looper.getMainLooper()).post { this.invoke(result) }
    }

    /**
     * Perform downloading the sample image.
     */
    abstract fun performDownload(request: DownloadResult<T>): P
}

/**
 * Class for performing sample image download using [Post] instance.
 */
class SampleImageDownloadController(
    coroutineScope: CoroutineScope, private val repository: ImageRepository
) : DownloadDoubleController<Post, Bitmap>(coroutineScope) {

    /**
     * Perform downloading the sample image.
     */
    override fun performDownload(request: DownloadResult<Post>): Bitmap {
        val byteArray = repository.get(request.data!!.sampleUrl)!!
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}

/**
 * Class for performing preview image download using [Post] instance.
 */
class PreviewImageDownloadController(
    coroutineScope: CoroutineScope, private val repository: ImageRepository
): DownloadDoubleController<Post, Bitmap>(coroutineScope) {
    override fun performDownload(request: DownloadResult<Post>): Bitmap {
        val byteArray = repository.get(request.data!!.previewUrl)!!
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}

/**
 * Class for performing file download using [Post] instance and return a byte array as a result.
 */
class FileImageDownloadController(
    coroutineScope: CoroutineScope, private val repository: ImageRepository
) : DownloadDoubleController<Post, ByteArray>(coroutineScope) {

    /**
     * Perform downloading the sample image.
     */
    override fun performDownload(request: DownloadResult<Post>): ByteArray {
        return repository.get(request.data!!.fileUrl)!!
    }
}

/**
 * Class for downloading [Posts].
 */
class PostsDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: PostsRepository
): DownloadDoubleController<Int, Posts>(coroutineScope) {

    @Synchronized
    override fun performDownload(request: DownloadResult<Int>): Posts {
        return repository.get(request.data!!)
    }
}

/**
 * Any download will be wrapped in this class.
 */
data class DownloadResult<T>(val data: T? = null, val exception: Exception? = null)