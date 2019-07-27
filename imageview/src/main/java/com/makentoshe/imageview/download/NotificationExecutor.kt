package com.makentoshe.imageview.download

/** Performs a notification displaying */
interface NotificationExecutor {
    /** Calls on downloading success */
    fun notifySuccess(id: Long, title: String, content: String, image: ByteArray)

    /** Calls on downloading progress. Pass a negative value to a [progress] for making it indeterminate */
    fun notifyProgress(id: Long, title: String, content: String, progress: Int)

    /** Calls on downloading error */
    fun notifyError(id: Long, title: String, exception: Throwable)
}