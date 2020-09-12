package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.deserialize.JsonDanbooruPostsDeserializer
import post.network.DanbooruPostsFilter
import post.network.DanbooruPostsRequest
import post.network.JsonDanbooruPostsNetworkManager
import post.network.JsonDanbooruPostsResponse

class JsonDanbooruPostsNetworkManagerTest {

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json posts`() = runBlocking {
        val request = DanbooruPostsRequest.Json(DanbooruPostsFilter(count = 10))
        val response = JsonDanbooruPostsNetworkManager(HttpClient()).getPosts(request)

        // deserialize json and check: was the filter condition satisfied?
        val posts = JsonDanbooruPostsDeserializer().deserializePosts(response as JsonDanbooruPostsResponse.Success)
        assertEquals(10, posts.posts.size)
    }
}