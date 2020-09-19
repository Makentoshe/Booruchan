package post

import junit.framework.Assert.assertEquals
import org.junit.Test
import post.deserialize.JsonGelbooruPostsDeserializer
import post.network.JsonGelbooruPostsResponse

class JsonGelbooruPostsDeserializerTest {

    @Test
    fun `should parse json posts`() {
        val json = javaClass.classLoader.getResource("posts.json")!!.readText()
        val deserialize = JsonGelbooruPostsDeserializer().deserializePosts(JsonGelbooruPostsResponse.Success(json))

        assertEquals(20, deserialize.posts.size)
    }

    @Test
    fun `should parse corrupted json posts`() {
        val json = javaClass.classLoader.getResource("posts-corrupted.json")!!.readText()
        val deserialize = JsonGelbooruPostsDeserializer().deserializePosts(JsonGelbooruPostsResponse.Success(json))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(2, deserialize.failures.size)
        assertEquals(18, deserialize.posts.size)
    }
}
