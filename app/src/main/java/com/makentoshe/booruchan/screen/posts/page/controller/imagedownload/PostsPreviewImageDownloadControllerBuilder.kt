package com.makentoshe.booruchan.screen.posts.page.controller.imagedownload

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.repository.factory.RepositoryFactory
import io.reactivex.disposables.CompositeDisposable

/**
 * Factory for creating preview image download controllers.
 *
 * @param disposables is a local disposables container which will be disposed each [Fragment.onDestroy].
 */
class PostsPreviewImageDownloadControllerBuilder(private val disposables: CompositeDisposable) {

    /**
     * Creates a controller instance with the current repository as a source.
     */
    fun buildController(repositoryFactory: RepositoryFactory): PostsPreviewImageDownloadController {
        return PostsPreviewImageDownloadControllerImpl(repositoryFactory, disposables)
    }
}