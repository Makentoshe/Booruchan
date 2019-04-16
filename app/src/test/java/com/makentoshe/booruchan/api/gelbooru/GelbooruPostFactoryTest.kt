package com.makentoshe.booruchan.api.gelbooru

import com.makentoshe.booruchan.api.component.post.PostTagsParser
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GelbooruPostFactoryTest {

    private lateinit var factory: GelbooruPostFactory

    @Before
    fun init() {
        val factory = GelbooruTagFactory()
        val postTagsParser = PostTagsParser(factory)
        this.factory = GelbooruPostFactory(postTagsParser)
    }

    @Test
    fun `should build post from map`() {
        val post = factory.build("id" to "1234", "score" to "5", "tags" to "sas asa psa")

        assertEquals(post.id, 1234L)
        assertEquals(post.score, 5)
    }
}