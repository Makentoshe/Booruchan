package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.factory.RepositoryFactory
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
    private val repositoryFactory: RepositoryFactory
) {

    /**
     * Returns a grid element controller with the started preview downloading process.
     */
    fun createController(post: Post): PostPageGridElementController {
        val downloadController = downloadControllerFactory.buildController(repositoryFactory)
        //start downloading
        downloadController.start(post)
        return PostPageGridElementController(downloadController)
    }
}