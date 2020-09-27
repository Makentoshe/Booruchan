package comment

import comment.deserialize.XmlDanbooruCommentDeserializer
import deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruCommentDeserializerTest {

    @Test
    fun `should parse xml comment with success result`() {
        val xml = javaClass.classLoader.getResource("comment.xml")!!.readText()
        val deserializeResult = XmlDanbooruCommentDeserializer().deserializeComment(xml)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(1, deserializeSuccess.comment.commentId)
        assertEquals(162, deserializeSuccess.comment.postId)
    }

    @Test
    fun `should parse xml corrupted comment with exception result`() {
        val xml = javaClass.classLoader.getResource("comment-corrupted.xml")!!.readText()
        val deserializeResult = XmlDanbooruCommentDeserializer().deserializeComment(xml)
        val deserializeFailure = deserializeResult.exceptionOrNull() as EntityDeserializeException

        // TODO mb add asserts for all fields?
        assertEquals(11, deserializeFailure.raw.size)
    }
}