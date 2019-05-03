package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.RepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadController
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

/**
 * Factory creates a grid element controller.
 *
 * @param compositeDisposable is a disposable where all subscribers contains while they will not being
 * disposed.
 */
class GridElementControllerBuilder(
    private val repositoryFactory: RepositoryFactory,
    private val compositeDisposable: CompositeDisposable
) : KoinComponent {

    /**
     * Returns a grid element controller with the started preview downloading process.
     */
    fun createController(post: Post): GridElementController {
        val downloadController = buildDownloadController(post)
        val typeController = GridElementTypeController(post)
        return GridElementController(downloadController, typeController)
    }

    private fun buildDownloadController(post: Post): PreviewImageDownloadController {
        val downloadController = PreviewImageDownloadController.build(repositoryFactory, compositeDisposable)
        //start downloading
        downloadController.start(post)
        return downloadController
    }
}