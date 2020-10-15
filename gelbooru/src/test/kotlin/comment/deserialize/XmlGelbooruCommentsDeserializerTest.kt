package comment.deserialize

import deserialize.DeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlGelbooruCommentsDeserializerTest {

    @Test
    fun `should deserialize comments`() {
        val xml = javaClass.classLoader.getResource("comments.xml")!!.readText()
        val deserialize = XmlGelbooruCommentsDeserializer().deserializeComments(xml)
        val successDeserialize = deserialize.getOrNull()!!

        assertEquals(20, successDeserialize.deserializes.size)
        assertEquals(20, successDeserialize.comments.size)
        assertEquals(0, successDeserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted comments`() {
        val xml = javaClass.classLoader.getResource("comments-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruCommentsDeserializer().deserializeComments(xml)
        val successDeserialize = deserialize.getOrNull()!!

        assertEquals(20, successDeserialize.deserializes.size)
        assertEquals(18, successDeserialize.comments.size)
        assertEquals(2, successDeserialize.failures.size)
    }

    @Test(expected = DeserializeException::class)
    fun `should return exception result on invalid xml`() {
        val xml = "invalid xml"
        val deserialize = XmlGelbooruCommentsDeserializer().deserializeComments(xml)
        throw deserialize.exceptionOrNull()!!
    }
}