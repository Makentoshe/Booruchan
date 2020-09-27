package comment

import comment.deserialize.JsonDanbooruCommentDeserializer
import deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruCommentDeserializerTest {

    @Test
    fun `should parse json`() {
        val json = javaClass.classLoader.getResource("comment.json")!!.readText()
        val deserializeResult = JsonDanbooruCommentDeserializer().deserializeComment(json)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(1, deserializeSuccess.comment.commentId)
        assertEquals(162, deserializeSuccess.comment.postId)
    }

    @Test
    fun `should parse corrupted json`() {
        val json = javaClass.classLoader.getResource("comment-corrupted.json")!!.readText()
        val deserializeResult = JsonDanbooruCommentDeserializer().deserializeComment(json)
        val deserializeFailure = deserializeResult.exceptionOrNull()!! as EntityDeserializeException

        // TODO mb add asserts for all fields?
        assertEquals(10, deserializeFailure.raw.size)
    }
}