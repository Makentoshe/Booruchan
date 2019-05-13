package com.makentoshe.booruchan.screen.posts.page.controller.gridelement

import com.makentoshe.booruchan.common.download.ImageDownloadListener
import com.makentoshe.booruchan.model.StreamDownloadListener
import io.mockk.*
import org.junit.Before
import org.junit.Test

class GridElementControllerTest {

    private lateinit var controller: GridElementController
    private lateinit var imageListener: ImageDownloadListener
    private lateinit var streamListener: StreamDownloadListener

    @Before
    fun init() {
        imageListener = mockk()
        every { imageListener.onSuccess(any()) } just Runs
        every { imageListener.onError(any()) } just Runs

        streamListener = mockk()
        every { streamListener.onPartReceived(any()) } just Runs
        every { streamListener.onComplete(any()) } just Runs
        every { streamListener.onError(any()) } just Runs

        controller = spyk(GridElementController(imageListener, streamListener))
    }

    @Test
    fun shouldInvokeListeners() {
        controller.bindControllers(mockk(), mockk())

        verifyAll {
            streamListener.onPartReceived(any())
            imageListener.onSuccess(any())
            imageListener.onError(any())
        }
    }
}