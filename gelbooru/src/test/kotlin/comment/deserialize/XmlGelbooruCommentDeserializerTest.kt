package comment.deserialize

import deserialize.DeserializeException
import deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlGelbooruCommentDeserializerTest {

    @Test
    fun `should deserialize comment`() {
        val xml = javaClass.classLoader.getResource("comment.xml")!!.readText()
        val deserialize = XmlGelbooruCommentDeserializer().deserializeComment(xml)
        val successDeserialize = deserialize.getOrNull()!!

        // todo add asserts for all fields
        assertEquals(2565334, successDeserialize.comment.commentId)
    }

    @Test
    fun `should deserialize variant comment`() {
        val xml = javaClass.classLoader.getResource("comment-variant.xml")!!.readText()
        val deserialize = XmlGelbooruCommentDeserializer().deserializeComment(xml)
        val successDeserialize = deserialize.getOrNull()!!

        // todo add asserts for all fields
        assertEquals(2565334, successDeserialize.comment.commentId)
    }

    @Test
    fun `should deserialize corrupted comment`() {
        val xml = javaClass.classLoader.getResource("comment-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruCommentDeserializer().deserializeComment(xml)
        val failureDeserialize = deserialize.exceptionOrNull() as EntityDeserializeException

        assertEquals(6, failureDeserialize.raw.size)
    }

    @Test(expected = DeserializeException::class)
    fun `should return exception result on invalid xml`() {
        val xml = "invalid xml"
        val deserialize = XmlGelbooruCommentDeserializer().deserializeComment(xml)
        throw deserialize.exceptionOrNull()!!
    }
}