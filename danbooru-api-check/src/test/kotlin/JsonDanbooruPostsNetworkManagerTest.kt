package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.network.DanbooruPostsFilter
import post.network.DanbooruPostsRequest
import post.network.JsonDanbooruPostsNetworkManager

class JsonDanbooruPostsNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json posts`() = runBlocking {
        val request = DanbooruPostsRequest.Json(DanbooruPostsFilter(count = 10))
        val response = JsonDanbooruPostsNetworkManager(HttpClient()).getPosts(request)
        println(response)
    }
}