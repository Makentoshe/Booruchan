package post

import org.junit.Assert
import org.junit.Test
import post.network.XmlDanbooruPostsResponse

class XmlDanbooruPostsDeserializerTest {

    @Test
    fun `should parse xml posts`() {
        val xml = javaClass.classLoader.getResource("posts.xml")!!.readText()
        val posts = XmlDanbooruPostsDeserializer().deserializePosts(XmlDanbooruPostsResponse.Success(xml))

        Assert.assertEquals(20, posts.posts.size)
    }
}