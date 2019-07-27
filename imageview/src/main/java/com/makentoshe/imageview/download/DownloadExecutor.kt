package com.makentoshe.imageview.download

import com.makentoshe.boorulibrary.entitiy.Post

/** Interface for executor, performs a file downloading*/
interface DownloadExecutor {
    /**
     * Performs a file downloading from [post] and return a result in the callback.
     * If argument is null - the downloading was successful.
     */
    fun download(post: Post, callback: (Throwable?) -> Unit = {})
}