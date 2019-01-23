package com.makentoshe.booruchan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruapi.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface Controller<P, T> {
    fun subscribe(param: P, action: (T?) -> Unit)
}

abstract class DownloadController<P>(protected val coroutineScope: CoroutineScope): Controller<P, Bitmap>

class ImageDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: ImageRepository
): DownloadController<Post>(coroutineScope) {

    override fun subscribe(param: Post, action: (Bitmap?) -> Unit) {
        coroutineScope.launch {
            try {
                val byteArray = repository.get(param.sampleUrl)!!
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                Handler(Looper.getMainLooper()).post { action(bitmap) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}