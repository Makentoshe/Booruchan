package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.XmlGelbooruPostDeserialize
import post.deserialize.XmlGelbooruPostDeserializer
import post.network.XmlGelbooruPostResponse

class XmlGelbooruPostDeserializerTest {

    @Test
    fun `should parse xml to the success`() {
        val xml = javaClass.classLoader.getResource("post.xml")!!.readText()
        val deserialize = XmlGelbooruPostDeserializer().deserializePost(XmlGelbooruPostResponse.Success(xml))
        val successDeserialize = deserialize as XmlGelbooruPostDeserialize.Success

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(1, successDeserialize.post.postId)
    }

    @Test
    fun `should parse corrupted xml with required param to the failure`() {
        val xml = javaClass.classLoader.getResource("post-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruPostDeserializer().deserializePost(XmlGelbooruPostResponse.Success(xml))
        val failureDeserialize = deserialize as XmlGelbooruPostDeserialize.Failure

        // TODO mb add asserts for all fields?
        assertEquals(23, failureDeserialize.raw.size)
    }
}