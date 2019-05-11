package com.makentoshe.booruchan.screen.posts.container.controller

import com.makentoshe.booruchan.repository.cache.InternalCache
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import org.koin.test.KoinTest

class CacheControllerTest : KoinTest {

    @Test
    fun shouldClearCaches() {
        val cache = mockk<InternalCache<*, *>>()
        every { cache.clear() } just Runs

        CacheController.create(cache, context = Dispatchers.Main).clearAll()

        verify { cache.clear() }
    }

}