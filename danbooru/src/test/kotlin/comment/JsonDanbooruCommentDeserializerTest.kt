package comment

import comment.deserialize.DanbooruCommentDeserialize
import comment.deserialize.JsonDanbooruCommentDeserialize
import comment.deserialize.JsonDanbooruCommentDeserializer
import comment.network.JsonDanbooruCommentResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruCommentDeserializerTest {

    @Test
    fun `should parse json`() {
        val json = javaClass.classLoader.getResource("comment.json")!!.readText()
        val deserialize =
            JsonDanbooruCommentDeserializer().deserializeComment(JsonDanbooruCommentResponse.Success(json))
        val successDeserialize = deserialize as DanbooruCommentDeserialize.Success<*>

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(1, successDeserialize.comment.commentId)
        assertEquals(162, successDeserialize.comment.postId)
    }

    @Test
    fun `should parse corrupted json`() {
        val json = javaClass.classLoader.getResource("comment-corrupted.json")!!.readText()
        val deserialize =
            JsonDanbooruCommentDeserializer().deserializeComment(JsonDanbooruCommentResponse.Success(json))
        val failureDeserialize = deserialize as JsonDanbooruCommentDeserialize.Failure

        // TODO mb add asserts for all fields?
        assertEquals(10, failureDeserialize.raw.size)
    }
}