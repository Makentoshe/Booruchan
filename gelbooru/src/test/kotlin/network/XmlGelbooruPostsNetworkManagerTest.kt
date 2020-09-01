package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test

class GelbooruPostsNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun xml() = runBlocking {
        val request = GelbooruPostsRequest.Xml(10)
        val response = XmlGelbooruPostsNetworkManager(HttpClient()).posts(request)
        println(response)
    }

    @Test
    @Ignore("real api")
    fun json() = runBlocking {
        val request = GelbooruPostsRequest.Json()
        val response = JsonGelbooruPostsNetworkManager(HttpClient()).posts(request)
        println(response)
    }
}