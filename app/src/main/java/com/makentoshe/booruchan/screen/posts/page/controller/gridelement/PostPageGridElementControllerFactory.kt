package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PostsPreviewImageDownloadControllerFactory

/**
 * Factory creates a grid element controller.
 *
 * @param downloadControllerFactory is a factory which created an associated with
 * the current grid element download controller for downloading a preview image.
 * @param repository is a preview images source.
 */
class PostPageGridElementControllerFactory(
    private val downloadControllerFactory: PostsPreviewImageDownloadControllerFactory,
    private val repository: Repository<Post, ByteArray>
) {

    /**
     * Returns a grid element controller with the started preview downloading process.
     */
    fun createController(post: Post): PostPageGridElementController {
        val downloadController = downloadControllerFactory.buildController(repository)
        //start downloading
        downloadController.start(post)
        return PostPageGridElementController(downloadController)
    }
}