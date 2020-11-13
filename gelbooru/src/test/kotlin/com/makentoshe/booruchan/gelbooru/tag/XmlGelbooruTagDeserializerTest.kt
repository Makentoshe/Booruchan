package com.makentoshe.booruchan.gelbooru.tag

import com.makentoshe.booruchan.core.deserialize.DeserializeException
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlGelbooruTagDeserializerTest {

    @Test
    fun `should deserialize tag`() {
        val xml = javaClass.classLoader.getResource("tag.xml")!!.readText()
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(xml)
        val successDeserialize = deserialize.getOrNull()!!

        // todo add asserts for all fields
        assertEquals(651094, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize variant tag`() {
        val xml = javaClass.classLoader.getResource("tag-variant.xml")!!.readText()
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(xml)
        val successDeserialize = deserialize.getOrNull()!!

        // todo add asserts for all fields
        assertEquals(45162, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize corrupted tag`() {
        val xml = javaClass.classLoader.getResource("tag-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(xml)
        val failureDeserialize = deserialize.exceptionOrNull() as EntityDeserializeException

        assertEquals(5, failureDeserialize.raw.size)
    }

    @Test(expected = DeserializeException::class)
    fun `should return failure on invalid xml`() {
        val xml = "invalid xml"
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(xml)
        throw deserialize.exceptionOrNull()!!
    }
}