package comment

import comment.deserialize.DanbooruCommentDeserialize
import comment.deserialize.XmlDanbooruCommentDeserialize
import comment.deserialize.XmlDanbooruCommentDeserializer
import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruCommentDeserializerTest {

    @Test
    fun `should parse xml comment`() {
        val xml = javaClass.classLoader.getResource("comment.xml")!!.readText()
        val deserialize = XmlDanbooruCommentDeserializer().deserializeComment(xml)
        val successDeserialize = deserialize as DanbooruCommentDeserialize.Success<*>

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(1, successDeserialize.comment.commentId)
        assertEquals(162, successDeserialize.comment.postId)
    }

    @Test
    fun `should parse xml corrupted comment`() {
        val xml = javaClass.classLoader.getResource("comment-corrupted.xml")!!.readText()
        val deserialize = XmlDanbooruCommentDeserializer().deserializeComment(xml)
        val failureDeserialize = deserialize as XmlDanbooruCommentDeserialize.Failure

        // TODO mb add asserts for all fields?
        assertEquals(11, failureDeserialize.raw.size)
    }
}