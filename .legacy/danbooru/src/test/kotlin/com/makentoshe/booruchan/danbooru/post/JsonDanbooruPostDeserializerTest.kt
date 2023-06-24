package com.makentoshe.booruchan.danbooru.post

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.danbooru.post.deserialize.JsonDanbooruPostDeserializer

class JsonDanbooruPostDeserializerTest {

    @Test
    fun `should parse json post`() {
        val json = javaClass.classLoader.getResource("post.json")!!.readText()
        val result = JsonDanbooruPostDeserializer().deserializePost(json)
        val successResult = result.getOrNull()!!

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(json, successResult.rawValue)
        assertEquals(4086764, successResult.post.postId)
    }

    @Test
    fun `should parse json corrupted post`() {
        val json = javaClass.classLoader.getResource("post-corrupted.json")!!.readText()
        val result = JsonDanbooruPostDeserializer().deserializePost(json)

        // TODO mb add asserts for all fields?
        assertEquals(44, (result.exceptionOrNull() as EntityDeserializeException).raw.size)
    }
}