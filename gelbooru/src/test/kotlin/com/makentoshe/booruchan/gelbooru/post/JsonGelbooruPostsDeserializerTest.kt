package com.makentoshe.booruchan.gelbooru.post

import junit.framework.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.gelbooru.post.deserialize.JsonGelbooruPostsDeserializer

class JsonGelbooruPostsDeserializerTest {

    @Test
    fun `should parse json posts`() {
        val json = javaClass.classLoader.getResource("posts.json")!!.readText()
        val result = JsonGelbooruPostsDeserializer().deserializePosts(json)
        val successResult = result.getOrNull()!!

        assertEquals(20, successResult.posts.size)
    }

    @Test
    fun `should parse corrupted json posts`() {
        val json = javaClass.classLoader.getResource("posts-corrupted.json")!!.readText()
        val result = JsonGelbooruPostsDeserializer().deserializePosts(json)
        val failureResult = result.getOrNull()!!

        assertEquals(20, failureResult.deserializes.size)
        assertEquals(2, failureResult.failures.size)
        assertEquals(18, failureResult.posts.size)
    }
}
