package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.network.GelbooruPostsRequest
import post.network.JsonGelbooruPostsNetworkManager

class JsonGelbooruPostsNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json posts`() = runBlocking {
        val request = GelbooruPostsRequest.Json()
        val response = JsonGelbooruPostsNetworkManager(HttpClient()).getPosts(request)
        println(response)
    }
}