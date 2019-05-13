package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.common.download.DownloadStrategy

/**
 * Class performs image downloading from repository.
 */
class ImageDownloadStrategy(
    private val downloadStrategy: DownloadStrategy
) : ImageDownloadListener {

    fun start(post: Post) = downloadStrategy.start(post)

    override fun onSuccess(action: (Bitmap) -> Unit) {
        downloadStrategy.onSuccess {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            action(bitmap)
        }
    }

    override fun onError(action: (Throwable) -> Unit) {
        downloadStrategy.onError(action)
    }
}

