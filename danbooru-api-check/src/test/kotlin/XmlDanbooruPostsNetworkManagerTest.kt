package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.network.DanbooruPostsFilter
import post.network.DanbooruPostsRequest
import post.network.XmlDanbooruPostsNetworkManager

class XmlDanbooruPostsNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun xml() = runBlocking {
        val request = DanbooruPostsRequest.Xml(DanbooruPostsFilter(count = 10))
        val response = XmlDanbooruPostsNetworkManager(HttpClient()).getPosts(request)

        println(response)
    }
}

