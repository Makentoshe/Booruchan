package com.makentoshe.booruchan.danbooru.tag

import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.danbooru.tag.deserialize.XmlDanbooruTagsDeserializer

class XmlDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags xml`() {
        val xml = javaClass.classLoader.getResource("tags.xml")!!.readText()
        val deserializeResult = XmlDanbooruTagsDeserializer().deserializeTags(xml)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(20, deserializeSuccess.tags.size)
        assertEquals(0, deserializeSuccess.failures.size)
    }

    @Test
    fun `should deserialize corrupted tags xml`() {
        val xml = javaClass.classLoader.getResource("tags-corrupted.xml")!!.readText()
        val deserializeResult = XmlDanbooruTagsDeserializer().deserializeTags(xml)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(18, deserializeSuccess.tags.size)
        assertEquals(2, deserializeSuccess.failures.size)
    }
}