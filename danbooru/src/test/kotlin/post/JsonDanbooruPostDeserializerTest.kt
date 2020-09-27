package post

import deserialize.DeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.JsonDanbooruPostDeserializer

class JsonDanbooruPostDeserializerTest {

    @Test
    fun `should parse json post`() {
        val json = javaClass.classLoader.getResource("post.json")!!.readText()
        val result = JsonDanbooruPostDeserializer().deserializePost(json)

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(4086764, result.getOrNull()!!.post.postId)
    }

    @Test
    fun `should parse json corrupted post`() {
        val json = javaClass.classLoader.getResource("post-corrupted.json")!!.readText()
        val result = JsonDanbooruPostDeserializer().deserializePost(json)

        // TODO mb add asserts for all fields?
        assertEquals(44, (result.exceptionOrNull() as DeserializeException).raw.size)
    }
}