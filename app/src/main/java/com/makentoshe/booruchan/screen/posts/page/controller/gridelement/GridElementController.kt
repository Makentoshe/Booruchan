package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.common.download.ImageDownloadListener
import com.makentoshe.booruchan.model.StreamDownloadListener
import com.makentoshe.booruchan.screen.posts.page.model.ImageViewController
import com.makentoshe.booruchan.screen.posts.page.model.StreamProgressBarController

/**
 * Controller for single grid element.
 *
 * @param imageListener is a preview downloading result imageListener.
 */
class GridElementController(
    private val imageListener: ImageDownloadListener,
    private val streamListener: StreamDownloadListener
) {

    /**
     * Binds view controllers and download event listeners.
     */
    fun bindControllers(imageController: ImageViewController, progressController: StreamProgressBarController) {
        //preview was downloaded successfully
        imageListener.onSuccess {
            imageController.onSuccess(it)
            progressController.onComplete()
        }

        streamListener.onPartReceived { _, _, progress ->
            progressController.onProgress(progress)
        }

        //preview was not downloaded or downloaded with the error
        imageListener.onError {
            imageController.onError(it)
            progressController.onError()
            it.printStackTrace()
        }
    }
}

