package post

import org.junit.Assert
import org.junit.Test
import post.network.XmlDanbooruPostResponse

class JsonDanbooruPostDeserializerTest {

    @Test
    fun `should parse json post`() {
        val json = javaClass.classLoader.getResource("post.json")!!.readText()
        val post = JsonDanbooruPostDeserializer().deserializePost(XmlDanbooruPostResponse.Success(json))

        // TODO add asserts for all fields (feelsbadman)
        Assert.assertEquals(4086764, post.postId)
    }
}