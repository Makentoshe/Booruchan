package post

import org.junit.Assert
import org.junit.Test
import post.network.JsonDanbooruPostsResponse

class JsonDanbooruPostsDeserializerTest {

    @Test
    fun `should parse json posts`() {
        val json = javaClass.classLoader.getResource("posts.json")!!.readText()
        val posts = JsonDanbooruPostsDeserializer().deserializePosts(JsonDanbooruPostsResponse.Success(json))

        // because one of the posts is corrupted, so we should know how to process this case
        Assert.assertEquals(19, posts.posts.size)
    }
}