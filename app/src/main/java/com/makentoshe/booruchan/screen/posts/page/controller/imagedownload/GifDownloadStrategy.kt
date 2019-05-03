package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import com.makentoshe.booruchan.api.component.post.Post
import pl.droidsonroids.gif.GifDrawable

/**
 * Class performs gif image downloading from repository.
 */
class GifDownloadStrategy(
    private val downloadStrategy: DownloadStrategy
) : GifDownloadListener {

    fun start(post: Post) = downloadStrategy.start(post)

    override fun onSuccess(action: (GifDrawable) -> Unit) {
        downloadStrategy.onSuccess { action(GifDrawable(it)) }
    }

    override fun onError(action: (Throwable) -> Unit) {
        downloadStrategy.onError(action)
    }
}