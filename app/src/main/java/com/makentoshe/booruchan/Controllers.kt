package com.makentoshe.booruchan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruapi.Post
import com.makentoshe.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

interface Controller<P, T> {
    fun subscribe(param: P, action: (T) -> Unit)
}

abstract class DownloadController<P>(protected val coroutineScope: CoroutineScope): Controller<P, Bitmap>

class ImageDownloadController(
    coroutineScope: CoroutineScope,
    private val repository: Repository<String, ByteArray>
): DownloadController<Post>(coroutineScope) {

    override fun subscribe(param: Post, action: (Bitmap) -> Unit) {
        coroutineScope.launch {
            try {
                val byteArray = repository.get(param.previewUrl)!!
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                Handler(Looper.getMainLooper()).post { action(bitmap) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}