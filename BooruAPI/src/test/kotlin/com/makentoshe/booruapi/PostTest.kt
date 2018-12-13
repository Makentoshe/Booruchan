package com.makentoshe.booruapi

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PostTest {

    private lateinit var map: HashMap<String, String>

    @Before
    fun init() {
        map = HashMap()
    }

    @Test
    fun `should create post with value "id" is 2`() {
        map["id"] = 2.toString()
        val post = Post(map)
        assertEquals(2, post.id)
    }

    @Test
    fun `should create post with value "score" is 2`() {
        map["score"] = 2.toString()
        val post = Post(map)
        assertEquals(2, post.score)
    }

    @Test
    fun `should create post with value "creator_id" is 2`() {
        map["creator_id"] = 2.toString()
        val post = Post(map)
        assertEquals(2, post.creatorId)
    }

    @Test
    fun `should create post with value "rating" is SAFE`() {
        map["rating"] = "s"
        val post = Post(map)
        assertEquals(Post.Rating.SAFE, post.rating)
    }

    @Test
    fun `should create post with value "rating" is QUESTIONABLE`() {
        map["rating"] = "q"
        val post = Post(map)
        assertEquals(Post.Rating.QUESTIONABLE, post.rating)
    }

    @Test
    fun `should create post with value "rating" is EXPLICIT`() {
        map["rating"] = "e"
        val post = Post(map)
        assertEquals(Post.Rating.EXPLICIT, post.rating)
    }

    @Test
    fun `should create post with value "rating" is not specified`() {
        map["rating"] = "sas"
        val post = Post(map)
        assertEquals(Post.Rating.UNSPECIFIED, post.rating)
    }

    @Test
    fun `should create post with value "preview_url" is "url"`() {
        map["preview_url"] = "url"
        val post = Post(map)
        assertEquals("url", post.previewUrl)
    }

    @Test
    fun `should create post with value "sample_url" is "url"`() {
        map["sample_url"] = "url"
        val post = Post(map)
        assertEquals("url", post.sampleUrl)
    }

    @Test
    fun `should create post with value "file_url" is "url"`() {
        map["file_url"] = "url"
        val post = Post(map)
        assertEquals("url", post.fileUrl)
    }

    @Test
    fun `should create post with value "tags" is "sas asa"`() {
        map["tags"] = "sas asa"
        val post = Post(map)
        assertEquals(2, post.tags.size)
        assertEquals("sas", post.tags[0].name)
        assertEquals("asa", post.tags[1].name)
    }

    @Test
    fun `should create default post`() {
        val post = Post()
        assertEquals(-1, post.id)
        assertEquals(0, post.score)
        assertEquals(-1, post.creatorId)
        assertEquals("", post.previewUrl)
        assertEquals("", post.sampleUrl)
        assertEquals("", post.fileUrl)
        assertTrue(post.tags.isEmpty())
        assertEquals(Post.Rating.UNSPECIFIED, post.rating)
    }
}