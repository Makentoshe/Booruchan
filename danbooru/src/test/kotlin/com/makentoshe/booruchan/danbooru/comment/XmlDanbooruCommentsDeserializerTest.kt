package com.makentoshe.booruchan.danbooru.comment

import com.makentoshe.booruchan.danbooru.comment.deserialize.XmlDanbooruCommentsDeserializer
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruCommentsDeserializerTest {

    @Test
    fun `should deserialize xml with success result`() {
        val xml = javaClass.classLoader.getResource("comments.xml")!!.readText()
        val deserializeResult = XmlDanbooruCommentsDeserializer().deserializeComments(xml)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(20, deserializeSuccess.comments.size)
        assertEquals(0, deserializeSuccess.failures.size)
    }

    @Test
    fun `should deserialize corrupted xml with success result`() {
        val xml = javaClass.classLoader.getResource("comments-corrupted.xml")!!.readText()
        val deserializeResult = XmlDanbooruCommentsDeserializer().deserializeComments(xml)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(18, deserializeSuccess.comments.size)
        assertEquals(2, deserializeSuccess.failures.size)
    }
}