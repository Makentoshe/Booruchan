package post

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import post.deserialize.DanbooruPostDeserialize
import post.deserialize.XmlDanbooruPostDeserialize
import post.deserialize.XmlDanbooruPostDeserializer
import post.network.XmlDanbooruPostResponse

class XmlDanbooruPostDeserializerTest {

    @Test
    fun `should parse xml post`() {
        val xml = javaClass.classLoader.getResource("post.xml")!!.readText()
        val deserialize = XmlDanbooruPostDeserializer().deserializePost(XmlDanbooruPostResponse.Success(xml))
        val successDeserialize = deserialize as DanbooruPostDeserialize.Success<*>

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(4086764, successDeserialize.post.postId)
    }

    @Test
    fun `should parse xml corrupted post`() {
        val xml = javaClass.classLoader.getResource("post-corrupted.xml")!!.readText()
        val deserialize = XmlDanbooruPostDeserializer().deserializePost(XmlDanbooruPostResponse.Success(xml))
        val failureDeserialize = deserialize as XmlDanbooruPostDeserialize.Failure

        // TODO mb add asserts for all fields?
        assertEquals(44, failureDeserialize.raw.size)
    }
}