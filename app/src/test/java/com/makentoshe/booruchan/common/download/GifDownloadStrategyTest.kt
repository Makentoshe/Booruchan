package com.makentoshe.booruchan.common.download

import com.makentoshe.booruchan.api.component.post.Post
import io.mockk.*
import org.junit.Before
import org.junit.Test

class GifDownloadStrategyTest {

    private lateinit var gifStrategy: GifDownloadStrategy
    private lateinit var strategy: DownloadStrategy

    @Before
    fun init() {
        strategy = mockk()

        every { strategy.start(any()) } just Runs
        every { strategy.onSuccess(any()) } just Runs
        every { strategy.onError(any()) } just Runs

        gifStrategy = GifDownloadStrategy(strategy)
    }

    @Test
    fun shouldStartBaseStrategyOnStart() {
        val post = mockk<Post>()
        gifStrategy.start(post)
        verify { strategy.start(post) }
    }

    @Test
    fun shouldCallBaseOnSuccess() {
        gifStrategy.onSuccess(mockk())
        verify { strategy.onSuccess(any()) }
    }

    @Test
    fun shouldCallBaseOnError() {
        gifStrategy.onError(mockk())
        verify { strategy.onError(any()) }
    }
}