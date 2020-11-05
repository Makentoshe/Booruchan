package com.makentoshe.booruchan.danbooru.post

import org.junit.Assert.assertEquals
import org.junit.Test
import com.makentoshe.booruchan.danbooru.post.deserialize.XmlDanbooruPostsDeserializer

class XmlDanbooruPostsDeserializerTest {

    @Test
    fun `should parse xml posts`() {
        val xml = javaClass.classLoader.getResource("posts.xml")!!.readText()
        val deserializeResult = XmlDanbooruPostsDeserializer().deserializePosts(xml)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(xml, deserializeSuccess.rawValue)
        assertEquals(20, deserializeSuccess.posts.size)
    }

    @Test
    fun `should parse xml corrupted posts`() {
        val xml = javaClass.classLoader.getResource("posts-corrupted.xml")!!.readText()
        val deserializeResult = XmlDanbooruPostsDeserializer().deserializePosts(xml)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(xml, deserializeSuccess.rawValue)
        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(1, deserializeSuccess.failures.size)
        assertEquals(19, deserializeSuccess.posts.size)
    }
}