package com.makentoshe.booruchan.gelbooru.post

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.gelbooru.post.deserialize.XmlGelbooruPostDeserializer

class XmlGelbooruPostDeserializerTest {

    @Test
    fun `should parse xml`() {
        val xml = javaClass.classLoader.getResource("post.xml")!!.readText()
        val result = XmlGelbooruPostDeserializer().deserializePost(xml)
        val successResult = result.getOrNull()!!

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(xml, successResult.rawValue)
        assertEquals(1, successResult.post.postId)
    }

    @Test
    fun `should parse variant xml`() {
        val xml = javaClass.classLoader.getResource("post-variant.xml")!!.readText()
        val result = XmlGelbooruPostDeserializer().deserializePost(xml)
        val successResult = result.getOrNull()!!

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(xml, successResult.rawValue)
        assertEquals(5612477, successResult.post.postId)
    }

    @Test
    fun `should parse corrupted xml with required param to the failure`() {
        val xml = javaClass.classLoader.getResource("post-corrupted.xml")!!.readText()
        val result = XmlGelbooruPostDeserializer().deserializePost(xml)
        val failureResult = result.exceptionOrNull() as EntityDeserializeException
        // TODO mb add asserts for all fields?
        assertEquals(23, failureResult.raw.size)
    }
}