package comment

import comment.deserialize.JsonDanbooruCommentsDeserializer
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruCommentsDeserializerTest {

    @Test
    fun `should deserialize json`() {
        val json = javaClass.classLoader.getResource("comments.json")!!.readText()
        val deserialize =
            JsonDanbooruCommentsDeserializer().deserializeComments(json)

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(20, deserialize.comments.size)
        assertEquals(0, deserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted json`() {
        val json = javaClass.classLoader.getResource("comments-corrupted.json")!!.readText()
        val deserialize =
            JsonDanbooruCommentsDeserializer().deserializeComments(json)

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(18, deserialize.comments.size)
        assertEquals(2, deserialize.failures.size)
    }
}
