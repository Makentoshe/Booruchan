package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import android.graphics.Bitmap

/**
 * Interface for posts preview download listeners.
 */
interface ImageDownloadListener {

    /**
     * Calls on preview image downloaded successfully.
     */
    fun onSuccess(action: (Bitmap) -> Unit)

    /**
     * Calls on preview image downloaded was finished with an error.
     */
    fun onError(action: (Throwable) -> Unit)
}