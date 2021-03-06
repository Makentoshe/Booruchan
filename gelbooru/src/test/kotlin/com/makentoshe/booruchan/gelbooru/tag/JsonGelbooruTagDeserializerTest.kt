package com.makentoshe.booruchan.gelbooru.tag

import com.makentoshe.booruchan.core.deserialize.DeserializeException
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.gelbooru.tag.deserialize.JsonGelbooruTagDeserializer
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonGelbooruTagDeserializerTest {

    @Test
    fun `should deserialize tag`() {
        val json = javaClass.classLoader.getResource("tag.json")!!.readText()
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(json)
        val successDeserialize = deserialize.getOrNull()!!

        // todo add asserts for all fields
        assertEquals(13710, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize variant tag`() {
        val json = javaClass.classLoader.getResource("tag-variant.json")!!.readText()
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(json)
        val successDeserialize = deserialize.getOrNull()!!

        // todo add asserts for all fields
        assertEquals(13710, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize corrupted tag`() {
        val json = javaClass.classLoader.getResource("tag-corrupted.json")!!.readText()
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(json)
        val failureDeserialize = deserialize.exceptionOrNull() as EntityDeserializeException

        assertEquals(5, failureDeserialize.raw.size)
    }

    @Test(expected = DeserializeException::class)
    fun `should return failure on invalid json`() {
        val json = "invalid json"
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(json)
        throw deserialize.exceptionOrNull()!!
    }
}