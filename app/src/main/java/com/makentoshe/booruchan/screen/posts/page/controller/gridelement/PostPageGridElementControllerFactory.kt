package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.factory.RepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.GridElementTypeController
import com.makentoshe.booruchan.screen.posts.page.GridElementTypeControllerFactory
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadControllerFactory

/**
 * Factory creates a grid element controller.
 *
 * @param downloadControllerFactory is a factory which created an associated with
 * the current grid element download controller for downloading a preview image.
 * @param repositoryFactory is a factory, creates any type of repositories.
 */
class PostPageGridElementControllerFactory(
    private val downloadControllerFactory: PostsPreviewImageDownloadControllerFactory,
    private val repositoryFactory: RepositoryFactory,
    private val typeControllerFactory: GridElementTypeControllerFactory
) {

    /**
     * Returns a grid element controller with the started preview downloading process.
     */
    fun createController(post: Post): PostPageGridElementController {
        val downloadController = buildDownloadController(post)
        val typeController = buildTypeController(post)
        return PostPageGridElementController(downloadController, typeController)
    }

    private fun buildDownloadController(post: Post): PostsPreviewImageDownloadController {
        val downloadController = downloadControllerFactory.buildController(repositoryFactory)
        //start downloading
        downloadController.start(post)
        return downloadController
    }

    private fun buildTypeController(post: Post): GridElementTypeController {
        return typeControllerFactory.buildController(post)
    }
}