package com.makentoshe.booruchan.api

import com.makentoshe.booruchan.api.entity.Post
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PostsTest {

    private lateinit var posts: Posts<TestPost>

    @Before
    fun init() {
        posts = Posts(393939, 0)
    }

    @Test
    fun `adding post`() {
        val post = TestPost()
        posts.addPost(post)
        assertEquals(1, posts.count())
    }

    @Test
    fun `getting post`() {
        val post = TestPost()
        posts.addPost(post)
        assertEquals(post, posts.getPost(0))
    }

    @Test
    fun `getting all posts count and offset`() {
        assertEquals(393939, posts.count)
        assertEquals(0, posts.offset)
    }




    private class TestPost: Post()
}