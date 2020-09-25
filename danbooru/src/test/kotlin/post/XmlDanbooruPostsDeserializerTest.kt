package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.XmlDanbooruPostsDeserializer

class XmlDanbooruPostsDeserializerTest {

    @Test
    fun `should parse xml posts`() {
        val xml = javaClass.classLoader.getResource("posts.xml")!!.readText()
        val deserialize = XmlDanbooruPostsDeserializer().deserializePosts(xml)

        assertEquals(20, deserialize.posts.size)
    }

    @Test
    fun `should parse xml corrupted posts`() {
        val xml = javaClass.classLoader.getResource("posts-corrupted.xml")!!.readText()
        val deserialize = XmlDanbooruPostsDeserializer().deserializePosts(xml)

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(1, deserialize.failures.size)
        assertEquals(19, deserialize.posts.size)
    }
}