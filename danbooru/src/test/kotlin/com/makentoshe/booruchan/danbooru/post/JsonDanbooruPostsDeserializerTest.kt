package com.makentoshe.booruchan.danbooru.post

import com.makentoshe.booruchan.danbooru.post.deserialize.JsonDanbooruPostsDeserializer
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruPostsDeserializerTest {

    @Test
    fun `should parse json posts`() {
        val json = javaClass.classLoader.getResource("posts.json")!!.readText()
        val result = JsonDanbooruPostsDeserializer().deserializePosts(json)
        val successResult = result.getOrNull()!!

        assertEquals(json, successResult.rawValue)
        assertEquals(20, successResult.deserializes.size)
    }

    @Test
    fun `should parse corrupted json posts`() {
        val json = javaClass.classLoader.getResource("posts-corrupted.json")!!.readText()
        val result = JsonDanbooruPostsDeserializer().deserializePosts(json)
        val successResult = result.getOrNull()!!

        assertEquals(json, successResult.rawValue)
        assertEquals(20, successResult.deserializes.size)
        assertEquals(2, successResult.failures.size)
        assertEquals(18, successResult.posts.size)
    }
}