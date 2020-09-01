package network

import org.junit.Assert
import org.junit.Test

class XmlGelbooruPostsDeserializerTest {

    @Test
    fun `should parse list of posts in xml`() {
        val xml = javaClass.classLoader.getResource("posts.xml")!!.readText()
        val posts = XmlGelbooruPostsDeserializer().deserializePosts(XmlGelbooruPostsResponse.Success(xml))

        Assert.assertEquals(5171560, posts.count)
        Assert.assertEquals(0, posts.offset)
        Assert.assertEquals(10, posts.posts.size)
    }
}