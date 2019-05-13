package com.makentoshe.booruchan.common.download

import com.makentoshe.booruchan.api.component.post.Post
import io.mockk.*
import org.junit.Before
import org.junit.Test

class ImageDownloadStrategyTest {

    private lateinit var imageStrategy: ImageDownloadStrategy
    private lateinit var strategy: DownloadStrategy

    @Before
    fun init() {
        strategy = mockk()

        every { strategy.start(any()) } just Runs
        every { strategy.onSuccess(any()) } just Runs
        every { strategy.onError(any()) } just Runs

        imageStrategy = ImageDownloadStrategy(strategy)
    }

    @Test
    fun shouldStartBaseStrategyOnStart() {
        val post = mockk<Post>()
        imageStrategy.start(post)
        verify { strategy.start(post) }
    }

    @Test
    fun shouldCallBaseOnSuccess() {
        imageStrategy.onSuccess(mockk())
        verify { strategy.onSuccess(any()) }
    }

    @Test
    fun shouldCallBaseOnError() {
        imageStrategy.onError(mockk())
        verify { strategy.onError(any()) }
    }
}