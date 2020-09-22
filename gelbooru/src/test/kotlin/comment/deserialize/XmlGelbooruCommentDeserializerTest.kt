package comment.deserialize

import comment.network.XmlGelbooruCommentResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlGelbooruCommentDeserializerTest {

    @Test
    fun `should deserialize comment`() {
        val xml = javaClass.classLoader.getResource("comment.xml")!!.readText()
        val deserialize = XmlGelbooruCommentDeserializer().deserializeComment(XmlGelbooruCommentResponse.Success(xml))
        val successDeserialize = deserialize as XmlGelbooruCommentDeserialize.Success

        // todo add asserts for all fields
        assertEquals(2565334, successDeserialize.comment.commentId)
    }

    @Test
    fun `should deserialize variant comment`() {
        val xml = javaClass.classLoader.getResource("comment-variant.xml")!!.readText()
        val deserialize = XmlGelbooruCommentDeserializer().deserializeComment(XmlGelbooruCommentResponse.Success(xml))
        val successDeserialize = deserialize as XmlGelbooruCommentDeserialize.Success

        // todo add asserts for all fields
        assertEquals(2565334, successDeserialize.comment.commentId)
    }

    @Test
    fun `should deserialize corrupted comment`() {
        val xml = javaClass.classLoader.getResource("comment-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruCommentDeserializer().deserializeComment(XmlGelbooruCommentResponse.Success(xml))
        val failureDeserialize = deserialize as XmlGelbooruCommentDeserialize.Failure

        assertEquals(6, failureDeserialize.raw.size)
    }
}