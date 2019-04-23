package com.makentoshe.booruchan.screen.posts.page.controller.postsdownload

/**
 * Controller starts posts downloading
 */
interface PostsDownloadController : PostsDownloadEventListener {
    /**
     * Starts posts downloading.
     */
    fun start()
}