package com.makentoshe.booruchan.danbooru.post

import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.danbooru.post.deserialize.XmlDanbooruPostDeserializer

class XmlDanbooruPostDeserializerTest {

    @Test
    fun `should parse xml post`() {
        val xml = javaClass.classLoader.getResource("post.xml")!!.readText()
        val result = XmlDanbooruPostDeserializer().deserializePost(xml)
        val successResult = result.getOrNull()!!

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(xml, successResult.rawValue)
        assertEquals(4086764, successResult.post.postId)
    }

    @Test
    fun `should parse xml corrupted post`() {
        val xml = javaClass.classLoader.getResource("post-corrupted.xml")!!.readText()
        val deserializeResult = XmlDanbooruPostDeserializer().deserializePost(xml)
        val failedDeserialize = deserializeResult.exceptionOrNull() as EntityDeserializeException
        // TODO mb add asserts for all fields?
        assertEquals(44, failedDeserialize.raw.size)
    }
}