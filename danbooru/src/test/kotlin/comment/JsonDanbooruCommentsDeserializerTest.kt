package comment

import comment.deserialize.JsonDanbooruCommentsDeserializer
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruCommentsDeserializerTest {

    @Test
    fun `should deserialize json with success result`() {
        val json = javaClass.classLoader.getResource("comments.json")!!.readText()
        val deserializeResult = JsonDanbooruCommentsDeserializer().deserializeComments(json)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(20, deserializeSuccess.comments.size)
        assertEquals(0, deserializeSuccess.failures.size)
    }

    @Test
    fun `should deserialize corrupted json with success result`() {
        val json = javaClass.classLoader.getResource("comments-corrupted.json")!!.readText()
        val deserializeResult = JsonDanbooruCommentsDeserializer().deserializeComments(json)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(20, deserializeSuccess.deserializes.size)
        assertEquals(18, deserializeSuccess.comments.size)
        assertEquals(2, deserializeSuccess.failures.size)
    }
}
