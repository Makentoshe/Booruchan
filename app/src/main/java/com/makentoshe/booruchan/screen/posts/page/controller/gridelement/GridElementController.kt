package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.model.StreamDownloadListener
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.ImageDownloadListener
import com.makentoshe.booruchan.screen.posts.page.model.ImageViewController
import com.makentoshe.booruchan.screen.posts.page.model.StreamProgressBarController

/**
 * Controller for single grid element.
 *
 * @param listener is a preview downloading result listener.
 */
class GridElementController(
    private val listener: ImageDownloadListener,
    private val streamListener: StreamDownloadListener
) {

    /**
     * Binds view controllers and download event listeners.
     */
    fun bindControllers(
        imageReceiveController: ImageViewController,
        streamProgressBarController: StreamProgressBarController
    ) {
        //preview was downloaded successfully
        listener.onSuccess {
            imageReceiveController.onSuccess(it)
            streamProgressBarController.onComplete()
        }

        streamListener.onPartReceived { _, _, progress ->
            streamProgressBarController.onProgress(progress)
        }

        listener.onError {
            imageReceiveController.onError(it)
            streamProgressBarController.onError()
            it.printStackTrace()
        }
    }
}

