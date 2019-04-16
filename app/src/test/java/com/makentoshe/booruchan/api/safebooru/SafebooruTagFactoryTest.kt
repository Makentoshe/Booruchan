package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.component.tag.TagFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SafebooruTagFactoryTest {

    private lateinit var factory: TagFactory<SafebooruTag>

    @Before
    fun init() {
        factory = SafebooruTagFactory()
    }

    @Test
    fun `should build tag from attributes`() {
        val tag = factory.build("name" to "sas")

        assertEquals("sas", tag.title)
    }
}