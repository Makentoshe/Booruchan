package com.makentoshe.booruchan.start.model.api.factory

import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GelbooruFactoryTest {

    @Test
    fun `create factory instance`() {
        val service = GelbooruFactory().createServiceInstance()
        assertTrue(service is Gelbooru)
    }

}