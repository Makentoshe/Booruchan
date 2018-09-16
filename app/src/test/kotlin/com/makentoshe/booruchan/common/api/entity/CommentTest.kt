package com.makentoshe.booruchan.common.api.entity

import com.makentoshe.booruchan.common.api.entity.Comment
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
open class CommentTest {

    private lateinit var comment: Comment

    @Before
    fun init() {
        val comment = object: Comment(){}
        comment.fill(generateData())
        this.comment = comment
    }

    protected fun generateData(): HashMap<String, String> {
        val data = HashMap<String, String>()
        data["creator_id"] = "293322"
        data["creator"] = "Username"
        data["post_id"] = "234123"
        data["id"] = "393939"
        data["body"] = "CommentBody"
        data["created_at"] = "CreatedAt"
        return data
    }

    @Test
    fun `should contain comment id`() {
        Assert.assertEquals(393939, comment.id)
    }

    @Test
    fun `should contain created_at`() {
        Assert.assertEquals("CreatedAt", comment.createdAt)
    }

    @Test
    fun `should contain creator_id`() {
        Assert.assertEquals(293322, comment.creatorId)
    }

    @Test
    fun `should contain raw data`() {
        assertNotNull(comment.raw)
    }

    @Test
    fun `should contain body`() {
        assertEquals("CommentBody", comment.body)
    }

    @Test
    fun `should contain post id`() {
        assertEquals(234123, comment.postId)
    }

    @Test
    fun `should contain creator name or something else`() {
        assertEquals("Username", comment.creator)
    }

}