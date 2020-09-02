package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.network.GelbooruPostRequest
import post.network.JsonGelbooruPostNetworkManager
import post.postId

class JsonGelbooruPostNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json post`() = runBlocking {
        val request = GelbooruPostRequest.Json(postId(2))
        val response = JsonGelbooruPostNetworkManager(HttpClient()).getPost(request)
        println(response)
    }

}