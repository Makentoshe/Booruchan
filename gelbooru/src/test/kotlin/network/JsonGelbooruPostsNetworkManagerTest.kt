package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.network.GelbooruPostsFilter
import post.network.GelbooruPostsRequest
import post.network.JsonGelbooruPostsNetworkManager

class JsonGelbooruPostsNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json posts`() = runBlocking {
        val request = GelbooruPostsRequest.Json(GelbooruPostsFilter(count = 20))
        val response = JsonGelbooruPostsNetworkManager(HttpClient()).getPosts(request)
        println(response)
    }
}