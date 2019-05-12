package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadController
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

/**
 * Factory creates a grid element controller.
 *
 * @param compositeDisposable is a disposable where all subscribers contains while they will not being
 * disposed.
 */
class GridElementControllerBuilder(
    private val booru: Booru,
    private val compositeDisposable: CompositeDisposable
) : KoinComponent {

    private val progressController = StreamDownloadController.create()

    private val repositoryFactory = get<StreamRepositoryFactory> {
        parametersOf(booru, progressController)
    }

    /**
     * Returns a grid element controller with the started preview downloading process.
     */
    fun createController(post: Post): GridElementController {
        val downloadController = buildDownloadController(post)
        return GridElementController(downloadController, post, progressController)
    }

    private fun buildDownloadController(post: Post): PreviewImageDownloadController {
        val downloadController = PreviewImageDownloadController.build(repositoryFactory, compositeDisposable)
        //start downloading
        downloadController.start(post)
        return downloadController
    }
}
