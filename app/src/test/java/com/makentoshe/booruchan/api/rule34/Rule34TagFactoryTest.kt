package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.tag.TagFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class Rule34TagFactoryTest {

    private lateinit var factory: TagFactory<Rule34Tag>

    @Before
    fun init() {
        factory = Rule34TagFactory()
    }

    @Test
    fun `should build tag from attributes`() {
        val tag = factory.build("name" to "sas")

        assertEquals("sas", tag.title)
    }
}