package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.postId

class GelbooruPostNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun xml() = runBlocking {
        val request = GelbooruPostRequest.Xml(postId(1))
        val response = XmlGelbooruPostNetworkManager(HttpClient()).post(request)
        println(response)
    }

    @Test
    @Ignore("real api")
    fun json() = runBlocking {
        val request = GelbooruPostRequest.Json(postId(2))
        val response = JsonGelbooruPostNetworkManager(HttpClient()).post(request)
        println(response)
    }
}