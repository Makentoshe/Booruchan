package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.DanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer

class JsonDanbooruPostDeserializerTest {

    @Test
    fun `should parse json post`() {
        val json = javaClass.classLoader.getResource("post.json")!!.readText()
        val deserialize = JsonDanbooruPostDeserializer().deserializePost(json)
        val successDeserialize = deserialize as DanbooruPostDeserialize.Success<*>

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(4086764, successDeserialize.post.postId)
    }

    @Test
    fun `should parse json corrupted post`() {
        val json = javaClass.classLoader.getResource("post-corrupted.json")!!.readText()
        val deserialize = JsonDanbooruPostDeserializer().deserializePost(json)
        val failureDeserialize = deserialize as JsonDanbooruPostDeserialize.Failure

        // TODO mb add asserts for all fields?
        assertEquals(44, failureDeserialize.raw.size)
    }
}