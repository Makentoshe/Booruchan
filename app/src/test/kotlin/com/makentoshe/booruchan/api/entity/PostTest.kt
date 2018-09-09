package com.makentoshe.booruchan.api.entity

import com.makentoshe.booruchan.api.entity.Post
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
open class PostTest {

    private lateinit var post: Post

    @Before
    fun init() {
        val post = object: Post(){}
        post.fill(generateData())
        this.post = post
    }

    @Test
    fun `should contain sample_url data`() {
        assertEquals("sampleUrl", post.sampleUrl)
    }

    @Test
    fun `should contain preview_url data`() {
        assertEquals("previewUrl", post.previewUrl)
    }

    @Test
    fun `should contain file_url data`() {
        assertEquals("fileUrl", post.fileUrl)
    }

    @Test
    fun `should contain id data`() {
        assertEquals(393939, post.id)
    }

    @Test
    fun `should contain created_at data`() {
        assertEquals("createdAt", post.createdAt)
    }

    @Test
    fun `should contain creator_id data`() {
        assertEquals(293322, post.creatorId)
    }

    @Test
    fun `should contain score data`() {
        assertEquals(4, post.score)
    }

    @Test
    fun `should contain has_comments data`() {
        assertEquals(true, post.hasComments)
    }

    @Test
    fun `should contain tags data`() {
        assertEquals(5, post.tags.size)
    }

    @Test
    fun `should contain raw data`() {
        assertNotNull(post.raw)
    }

    @Test
    fun `test rating parsing`() {
        val post = object : Post() {}
        val data = HashMap<String, String>()
        data["rating"] = "q"
        post.fill(data)
        assertEquals(Post.Rating.QUESTIONABLE, post.rating)
        data["rating"] = "s"
        post.fill(data)
        assertEquals(Post.Rating.SAFE, post.rating)
        data["rating"] = "e"
        post.fill(data)
        assertEquals(Post.Rating.EXPLICIT, post.rating)
        data["rating"] = "a"
        post.fill(data)
        assertEquals(Post.Rating.WTF, post.rating)
    }

    protected open fun generateData(): HashMap<String, String> {
        val data = HashMap<String, String>()
        data["sample_url"] = "sampleUrl"
        data["preview_url"] = "previewUrl"
        data["file_url"] = "fileUrl"
        data["id"] = "393939"
        data["created_at"] = "createdAt"
        data["creator_id"] = "293322"
        data["score"] = "4"
        data["has_comments"] = "true"
        data["tags"] = "hatsune_miku astarte shuvi idi hahoi"
        return data
    }

}