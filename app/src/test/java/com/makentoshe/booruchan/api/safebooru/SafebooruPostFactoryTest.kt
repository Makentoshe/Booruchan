package com.makentoshe.booruchan.api.safebooru

import com.makentoshe.booruchan.api.component.post.PostTagsParser
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SafebooruPostFactoryTest {

    private lateinit var factory: SafebooruPostFactory

    @Before
    fun init() {
        val factory = SafebooruTagFactory()
        val parser = PostTagsParser(factory)
        this.factory = SafebooruPostFactory(parser)
    }

    @Test
    fun `should build post from map`() {
        val post = factory.build("id" to "1234", "score" to "5", "tags" to "sas asa psa")

        Assert.assertEquals(post.id, 1234L)
        Assert.assertEquals(post.score, 5)
    }
}