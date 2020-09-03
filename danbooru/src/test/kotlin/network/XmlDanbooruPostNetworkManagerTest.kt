package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.network.DanbooruPostRequest
import post.network.XmlDanbooruPostNetworkManager
import post.postId

class XmlDanbooruPostNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request xml post`() = runBlocking {
        val request = DanbooruPostRequest.Xml(postId(1))
        val response = XmlDanbooruPostNetworkManager(HttpClient()).getPost(request)
        println(response)
    }
}

