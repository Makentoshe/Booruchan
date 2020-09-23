package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.JsonGelbooruPostDeserialize
import post.deserialize.JsonGelbooruPostDeserializer
import post.network.JsonGelbooruPostResponse

class JsonGelbooruPostDeserializerTest {

    @Test
    fun `should parse json`() {
        val json = javaClass.classLoader.getResource("post.json")!!.readText()
        val deserialize = JsonGelbooruPostDeserializer().deserializePost(JsonGelbooruPostResponse.Success(json))
        val successDeserialize = deserialize as JsonGelbooruPostDeserialize.Success

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(1, successDeserialize.post.postId)
    }

    @Test
    fun `should parse corrupted json`() {
        val json = javaClass.classLoader.getResource("post-corrupted.json")!!.readText()
        val deserialize = JsonGelbooruPostDeserializer().deserializePost(JsonGelbooruPostResponse.Success(json))
        val failureDeserialize = deserialize as JsonGelbooruPostDeserialize.Failure

        // TODO mb add asserts for all fields?
        assertEquals(18, failureDeserialize.raw.size)
    }
}