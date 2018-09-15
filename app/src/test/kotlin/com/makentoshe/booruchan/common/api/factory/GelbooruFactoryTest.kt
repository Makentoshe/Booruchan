package com.makentoshe.booruchan.common.api.factory

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GelbooruFactoryTest {

    @Test
    fun `creating factory with class`() {
        assertTrue(Factory.createFactory(Gelbooru::class.java as Class<Boor>) is GelbooruFactory)
    }

    @Test
    fun `creating factory with name`() {
        assertTrue(Factory.createFactory(Gelbooru::class.java.simpleName) is GelbooruFactory)
    }

    @Test
    fun `factory should return booru service`() {
        assertTrue(Factory.createFactory(Gelbooru::class.java.simpleName).createService() is Gelbooru)
    }
}