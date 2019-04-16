package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.component.tag.TagFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GelbooruTagFactoryTest {

    private lateinit var factory: TagFactory<GelbooruTag>

    @Before
    fun init() {
        factory = GelbooruTagFactory()
    }

    @Test
    fun `should build tag from attributes`() {
        val tag = factory.build("name" to "sas")

        assertEquals("sas", tag.title)
    }
}