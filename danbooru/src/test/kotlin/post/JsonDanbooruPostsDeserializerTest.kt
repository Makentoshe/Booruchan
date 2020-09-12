package post

import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.JsonDanbooruPostsDeserializer
import post.network.JsonDanbooruPostsResponse

class JsonDanbooruPostsDeserializerTest {

    @Test
    fun `should parse json posts`() {
        val json = javaClass.classLoader.getResource("posts.json")!!.readText()
        val posts = JsonDanbooruPostsDeserializer().deserializePosts(JsonDanbooruPostsResponse.Success(json))

        assertEquals(20, posts.rawPosts.size)
        // because one of the posts is corrupted, so we should know how to process this case
        assertEquals(19, posts.posts.size)
        assertEquals(1, posts.failures.size)
    }
}