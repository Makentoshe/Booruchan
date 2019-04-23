package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import com.makentoshe.booruchan.api.component.post.Post

/**
 * Controller start preview image downloading.
 */
interface PostsPreviewImageDownloadController : PostsPreviewImageDownloadEventListener {

    /**
     * Start preview image downloading using a post.
     */
    fun start(post: Post)
}

