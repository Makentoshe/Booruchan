package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.factory.RepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadController
import io.reactivex.disposables.CompositeDisposable

/**
 * Factory creates a grid element controller.
 *
 * @param compositeDisposable is a disposable where all subscribers contains while they will not being
 * disposed.
 * @param repositoryFactory is a factory, creates any type of repositories.
 * @param typeControllerBuilder is a factory, creates controller for controlling element types.
 */
class GridElementControllerBuilder(
    private val compositeDisposable: CompositeDisposable,
    private val repositoryFactory: RepositoryFactory,
    private val typeControllerBuilder: GridElementTypeControllerBuilder
) {

    /**
     * Returns a grid element controller with the started preview downloading process.
     */
    fun createController(post: Post): GridElementController {
        val downloadController = buildDownloadController(post)
        val typeController = buildTypeController(post)
        return GridElementController(downloadController, typeController)
    }

    private fun buildDownloadController(post: Post): PreviewImageDownloadController {
        val downloadController = PreviewImageDownloadController.build(repositoryFactory, compositeDisposable)
        //start downloading
        downloadController.start(post)
        return downloadController
    }

    private fun buildTypeController(post: Post): GridElementTypeController {
        return typeControllerBuilder.buildController(post)
    }
}