package com.makentoshe.booruchan.api.rule34

import com.makentoshe.booruchan.api.component.comment.CommentFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class Rule34CommentFactoryTest {

    private lateinit var factory: CommentFactory

    @Before
    fun init() {
        factory = Rule34CommentFactory()
    }

    @Test
    fun `should create comment`() {
        val comment = factory.build(
            "body" to "body", "id" to "3939", "creator" to "Makentoshe",
            "post_id" to "1234", "created_at" to "creation time", "creator_id" to "4321")

        assertEquals("body", comment.body)
        assertEquals(3939L, comment.id)
        assertEquals("Makentoshe", comment.creator)
        assertEquals(1234L, comment.postId)
        assertEquals("creation time", comment.createdAt)
        assertEquals(4321L, comment.creatorId)
    }
}