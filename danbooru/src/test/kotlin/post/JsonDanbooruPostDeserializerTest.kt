package post

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import post.deserialize.DanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer
import post.network.XmlDanbooruPostResponse

class JsonDanbooruPostDeserializerTest {

    @Test
    fun `should parse json post`() {
        val json = javaClass.classLoader.getResource("post.json")!!.readText()
        val deserialize = JsonDanbooruPostDeserializer().deserializePost(XmlDanbooruPostResponse.Success(json))
        val successDeserialize = deserialize as DanbooruPostDeserialize.Success<*>

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(4086764, successDeserialize.post.postId)
    }

    @Test
    @Ignore
    fun `should parse json corrupted post`() {
        val json = javaClass.classLoader.getResource("post-corrupted.json")!!.readText()
        val post = JsonDanbooruPostDeserializer().deserializePost(XmlDanbooruPostResponse.Success(json))

        println(post)
    }
}