package com.makentoshe.booruchan.start.model.api.factory

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FactoryTest {

    @Test
    fun `create Gelbooru service factory`() {
        val factory = Factory.createServiceInstance(Gelbooru::class.java as Class<Boor>)
        assertTrue(factory is GelbooruFactory)
    }

}