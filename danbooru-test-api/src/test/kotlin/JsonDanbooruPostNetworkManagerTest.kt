package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.network.DanbooruPostRequest
import post.network.JsonDanbooruPostNetworkManager
import post.postId

class JsonDanbooruPostNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json post`() = runBlocking {
        val request = DanbooruPostRequest.Json(postId(2))
        val response = JsonDanbooruPostNetworkManager(HttpClient()).getPost(request)
        println(response)
    }

    @Test
    fun test() {
        println("---------------------------------------------------------------------------- lol")
    }

}