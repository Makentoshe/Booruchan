package com.makentoshe.booruchan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruapi.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface Controller<P, T> {
    fun subscribe(param: P, action: (T) -> Unit)
}

data class DownloadResult<T>(val data: T? = null, val exception: Exception? = null)

abstract class DownloadController<P, T>(
    protected val coroutineScope: CoroutineScope
) : Controller<DownloadResult<P>, DownloadResult<T>>

class ImageDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: ImageRepository
) : DownloadController<Post, Bitmap>(coroutineScope) {

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

    private fun ((DownloadResult<Bitmap>) -> Unit).pushResult(result: DownloadResult<Bitmap>) {
        Handler(Looper.getMainLooper()).post { this.invoke(result) }
    }

    private fun performDownload(request: DownloadResult<Post>): Bitmap {
        val byteArray = repository.get(request.data!!.sampleUrl)!!
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}