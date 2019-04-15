package com.makentoshe.booruchan.api.component

import com.makentoshe.booruchan.api.component.parser.Parser
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.api.component.post.PostTagsParser
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PostTagsParserTest {

    private lateinit var parser: Parser<Array<Tag>>

    @Before
    fun init() {
        val factory = { it: String -> Tag.create(it) }
        parser = PostTagsParser(factory)
    }

    @Test
    fun `should parse tags in posts`() {
        val string = "sas asa tag psa"
        val result = parser.parse(string)

        assertEquals(4, result.size)
        assertEquals("sas", result[0].title)
        assertEquals("asa", result[1].title)
        assertEquals("tag", result[2].title)
        assertEquals("psa", result[3].title)
    }
}