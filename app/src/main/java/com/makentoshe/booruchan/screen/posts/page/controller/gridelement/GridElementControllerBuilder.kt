package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.factory.RepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadControllerBuilder

/**
 * Factory creates a grid element controller.
 *
 * @param downloadControllerBuilder is a factory which created an associated with
 * the current grid element download controller for downloading a preview image.
 * @param repositoryFactory is a factory, creates any type of repositories.
 * @param typeControllerBuilder is a factory, creates controller for controlling element types.
 */
class GridElementControllerBuilder(
    private val downloadControllerBuilder: PostsPreviewImageDownloadControllerBuilder,
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

    private fun buildDownloadController(post: Post): PostsPreviewImageDownloadController {
        val downloadController = downloadControllerBuilder.buildController(repositoryFactory)
        //start downloading
        downloadController.start(post)
        return downloadController
    }

    private fun buildTypeController(post: Post): GridElementTypeController {
        return typeControllerBuilder.buildController(post)
    }
}