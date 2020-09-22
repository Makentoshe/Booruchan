package comment

import comment.deserialize.XmlDanbooruCommentsDeserializer
import comment.network.XmlDanbooruCommentsResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruCommentsDeserializerTest {

    @Test
    fun `should deserialize xml`() {
        val xml = javaClass.classLoader.getResource("comments.xml")!!.readText()
        val deserialize = XmlDanbooruCommentsDeserializer().deserializeComments(XmlDanbooruCommentsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(20, deserialize.comments.size)
        assertEquals(0, deserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted xml`() {
        val xml = javaClass.classLoader.getResource("comments-corrupted.xml")!!.readText()
        val deserialize = XmlDanbooruCommentsDeserializer().deserializeComments(XmlDanbooruCommentsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(18, deserialize.comments.size)
        assertEquals(2, deserialize.failures.size)
    }
}