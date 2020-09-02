package network

import org.junit.Assert.assertEquals
import org.junit.Test
import post.XmlGelbooruPostsDeserializer
import post.network.XmlGelbooruPostsResponse

class XmlGelbooruPostsDeserializerTest {

    @Test
    fun `should parse list of posts in xml`() {
        val xml = javaClass.classLoader.getResource("posts.xml")!!.readText()
        val posts = XmlGelbooruPostsDeserializer().deserializePosts(XmlGelbooruPostsResponse.Success(xml))

        assertEquals(5171560, posts.count)
        assertEquals(0, posts.offset)
        assertEquals(10, posts.posts.size)
    }
}