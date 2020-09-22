package comment.deserialize

import comment.network.XmlGelbooruCommentsResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlGelbooruCommentsDeserializerTest {

    @Test
    fun `should deserialize comments`() {
        val xml = javaClass.classLoader.getResource("comments.xml")!!.readText()
        val deserialize = XmlGelbooruCommentsDeserializer().deserializeComments(XmlGelbooruCommentsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(20, deserialize.comments.size)
        assertEquals(0, deserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted comments`() {
        val xml = javaClass.classLoader.getResource("comments-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruCommentsDeserializer().deserializeComments(XmlGelbooruCommentsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(18, deserialize.comments.size)
        assertEquals(2, deserialize.failures.size)
    }
}