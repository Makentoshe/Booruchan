package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.network.XmlDanbooruPostResponse

class XmlDanbooruPostDeserializerTest {

    @Test
    fun `should parse xml post`() {
        val xml = javaClass.classLoader.getResource("post.xml")!!.readText()
        val post = XmlDanbooruPostDeserializer().deserializePost(XmlDanbooruPostResponse.Success(xml))

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(4086764, post.postId)
    }
}