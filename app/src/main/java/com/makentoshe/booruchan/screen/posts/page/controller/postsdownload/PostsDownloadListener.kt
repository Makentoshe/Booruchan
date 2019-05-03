package com.makentoshe.booruchan.screen.posts.page.controller.postsdownload

import com.makentoshe.booruchan.api.component.post.Post

/**
 * Interface for posts download listeners
 */
interface PostsDownloadListener {

    /**
     * Calls on posts downloaded successfully
     */
    fun onSuccess(action: (List<Post>) -> Unit)

    /**
     * Calls on posts downloaded was finished with an error
     */
    fun onError(action: (Throwable) -> Unit)
}