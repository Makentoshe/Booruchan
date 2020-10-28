package com.makentoshe.booruchan.danbooru.post

import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.danbooru.post.deserialize.JsonDanbooruPostsDeserializer

class JsonDanbooruPostsDeserializerTest {

    @Test
    fun `should parse json posts`() {
        val json = javaClass.classLoader.getResource("posts.json")!!.readText()
        val result = JsonDanbooruPostsDeserializer().deserializePosts(json)

        assertEquals(20, result.getOrNull()!!.deserializes.size)
    }

    @Test
    fun `should parse corrupted json posts`() {
        val json = javaClass.classLoader.getResource("posts-corrupted.json")!!.readText()
        val result = JsonDanbooruPostsDeserializer().deserializePosts(json).getOrNull()!!

        assertEquals(20, result.deserializes.size)
        assertEquals(2, result.failures.size)
        assertEquals(18, result.posts.size)
    }
}