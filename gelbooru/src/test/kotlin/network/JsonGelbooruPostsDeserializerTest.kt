package network

import org.junit.Assert
import org.junit.Test
import post.JsonGelbooruPostsDeserializer
import post.network.JsonGelbooruPostsResponse

class JsonGelbooruPostsDeserializerTest {

    @Test
    fun `should parse list of posts in json`() {
        val json = javaClass.classLoader.getResource("posts.json")!!.readText()
        val posts = JsonGelbooruPostsDeserializer().deserializePosts(JsonGelbooruPostsResponse.Success(json))

        Assert.assertEquals(10, posts.posts.size)
    }
}