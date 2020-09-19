package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.XmlGelbooruPostsDeserializer
import post.network.XmlGelbooruPostsResponse

class XmlGelbooruPostsDeserializerTest {

    @Test
    fun `should parse xml posts`() {
        val xml = javaClass.classLoader.getResource("posts.xml")!!.readText()
        val deserialize = XmlGelbooruPostsDeserializer().deserializePosts(XmlGelbooruPostsResponse.Success(xml))

        assertEquals(20, deserialize.posts.size)
    }

    @Test
    fun `should parse xml corrupted posts`() {
        val xml = javaClass.classLoader.getResource("posts-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruPostsDeserializer().deserializePosts(XmlGelbooruPostsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(2, deserialize.failures.size)
        assertEquals(18, deserialize.posts.size)
    }
}
