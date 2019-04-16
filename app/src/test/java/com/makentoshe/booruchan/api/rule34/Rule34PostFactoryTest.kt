package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.post.PostTagsParser
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class Rule34PostFactoryTest {

    private lateinit var factory: Rule34PostFactory

    @Before
    fun init() {
        val factory = Rule34TagFactory()
        val postTagsParser = PostTagsParser(factory)
        this.factory = Rule34PostFactory(postTagsParser)
    }

    @Test
    fun `should build post from map`() {
        val post = factory.build("id" to "1234", "score" to "5")

        Assert.assertEquals(post.id, 1234L)
        Assert.assertEquals(post.score, 5)
    }
}