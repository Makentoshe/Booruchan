package com.makentoshe.booruchan.gelbooru.post

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.gelbooru.post.deserialize.JsonGelbooruPostDeserializer

class JsonGelbooruPostDeserializerTest {

    @Test
    fun `should parse json`() {
        val json = javaClass.classLoader.getResource("post.json")!!.readText()
        val result = JsonGelbooruPostDeserializer().deserializePost(json)
        val successResult = result.getOrNull()!!

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(1, successResult.post.postId)
    }

    @Test
    fun `should parse corrupted json`() {
        val json = javaClass.classLoader.getResource("post-corrupted.json")!!.readText()
        val result = JsonGelbooruPostDeserializer().deserializePost(json)
        val failureResult = result.exceptionOrNull() as EntityDeserializeException

        // TODO mb add asserts for all fields?
        assertEquals(18, failureResult.raw.size)
    }
}