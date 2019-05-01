package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

interface DownloadListener<T> {

    /**
     * Calls on preview image downloaded successfully.
     */
    fun onSuccess(action: (T) -> Unit)

    /**
     * Calls on preview image downloaded was finished with an error.
     */
    fun onError(action: (Throwable) -> Unit)
}