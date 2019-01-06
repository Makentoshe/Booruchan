package com.makentoshe.booruchan.postpage.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PreviewsDownloadController(
    private val coroutineScope: CoroutineScope,
    private val previewsRepository: PreviewsRepository
) {

    fun subscribeOnPreview(post: Post, action: (Bitmap) -> Unit) {
        coroutineScope.launch {
            val byteArray = previewsRepository.get(post.previewUrl)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            Handler(Looper.getMainLooper()).post { action(bitmap) }
        }
    }

}