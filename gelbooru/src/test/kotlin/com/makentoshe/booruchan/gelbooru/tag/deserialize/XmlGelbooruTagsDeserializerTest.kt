package com.makentoshe.booruchan.gelbooru.tag.deserialize

import com.makentoshe.booruchan.core.deserialize.DeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlGelbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags`() {
        val xml = javaClass.classLoader.getResource("tags.xml")!!.readText()
        val deserialize = XmlGelbooruTagsDeserializer().deserializeTags(xml)
        val successDeserialize = deserialize.getOrNull()!!

        assertEquals(20, successDeserialize.deserializes.size)
        assertEquals(20, successDeserialize.tags.size)
        assertEquals(0, successDeserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted tags`() {
        val xml = javaClass.classLoader.getResource("tags-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruTagsDeserializer().deserializeTags(xml)
        val successDeserialize = deserialize.getOrNull()!!

        assertEquals(20, successDeserialize.deserializes.size)
        assertEquals(18, successDeserialize.tags.size)
        assertEquals(2, successDeserialize.failures.size)
    }

    @Test(expected = DeserializeException::class)
    fun `should return empty result on invalid xml`() {
        val xml = "invalid xml"
        val deserialize = XmlGelbooruTagsDeserializer().deserializeTags(xml)
        throw deserialize.exceptionOrNull()!!
    }
}