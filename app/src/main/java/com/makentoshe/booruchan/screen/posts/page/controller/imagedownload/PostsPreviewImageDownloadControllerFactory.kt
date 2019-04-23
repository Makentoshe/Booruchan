package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import io.reactivex.disposables.CompositeDisposable

/**
 * Factory for creating preview image download controllers.
 *
 * @param disposables is a local disposables container which will be disposed each [Fragment.onDestroy].
 */
class PostsPreviewImageDownloadControllerFactory(private val disposables: CompositeDisposable) {

    /**
     * Creates a controller instance with the current repository as a source.
     */
    fun buildController(repository: Repository<Post, ByteArray>): PostsPreviewImageDownloadController {
        return PostsPreviewImageDownloadControllerImpl(repository, disposables)
    }
}