package com.makentoshe.booruchan.danbooru.tag

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.danbooru.tag.deserialize.JsonDanbooruTagDeserializer

class JsonDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag json`() {
        val json = javaClass.classLoader.getResource("tag.json")!!.readText()
        val deserializeResult = JsonDanbooruTagDeserializer().deserializeTag(json)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        // todo add asserts for all fields
        assertEquals(1591223, deserializeSuccess.tag.tagId)
    }

    @Test
    fun `should deserialize corrupted tag json`() {
        val json = javaClass.classLoader.getResource("tag-corrupted.json")!!.readText()
        val deserializeResult = JsonDanbooruTagDeserializer().deserializeTag(json)
        val deserializeFailure = deserializeResult.exceptionOrNull() as EntityDeserializeException

        assertEquals(7, deserializeFailure.raw.size)
    }
}