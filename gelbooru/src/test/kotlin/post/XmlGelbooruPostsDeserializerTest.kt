package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.XmlGelbooruPostsDeserializer

class XmlGelbooruPostsDeserializerTest {

    @Test
    fun `should parse xml posts`() {
        val xml = javaClass.classLoader.getResource("posts.xml")!!.readText()
        val result = XmlGelbooruPostsDeserializer().deserializePosts(xml)
        val successResult = result.getOrNull()!!

        assertEquals(20, successResult.posts.size)
    }

    @Test
    fun `should parse xml corrupted posts`() {
        val xml = javaClass.classLoader.getResource("posts-corrupted.xml")!!.readText()
        val result = XmlGelbooruPostsDeserializer().deserializePosts(xml)
        val successResult = result.getOrNull()!!

        assertEquals(20, successResult.deserializes.size)
        assertEquals(2, successResult.failures.size)
        assertEquals(18, successResult.posts.size)
    }
}
