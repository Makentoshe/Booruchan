package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.XmlDanbooruPostsDeserializer
import post.network.XmlDanbooruPostsResponse

class XmlDanbooruPostsDeserializerTest {

    @Test
    fun `should parse xml posts`() {
        val xml = javaClass.classLoader.getResource("posts.xml")!!.readText()
        val posts = XmlDanbooruPostsDeserializer().deserializePosts(XmlDanbooruPostsResponse.Success(xml))

        assertEquals(20, posts.posts.size)
    }
}