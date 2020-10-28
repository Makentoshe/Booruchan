package com.makentoshe.booruchan.danbooru.tag

import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.danbooru.tag.deserialize.JsonDanbooruTagsDeserializer

class JsonDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags json`() {
        val json = javaClass.classLoader.getResource("tags.json")!!.readText()
        val deserializeResult = JsonDanbooruTagsDeserializer().deserializeTags(json)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(20, deserializeSuccess.tags.size)
        assertEquals(0, deserializeSuccess.failures.size)
    }

    @Test
    fun `should deserialize corrupted tags json`() {
        val json = javaClass.classLoader.getResource("tags-corrupted.json")!!.readText()
        val deserializeResult = JsonDanbooruTagsDeserializer().deserializeTags(json)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(18, deserializeSuccess.tags.size)
        assertEquals(2, deserializeSuccess.failures.size)
    }
}
