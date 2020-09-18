package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.network.GelbooruPostFilter
import post.network.GelbooruPostRequest
import post.network.XmlGelbooruPostNetworkManager
import post.postId

class XmlGelbooruPostNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request xml post`() = runBlocking {
        val request = GelbooruPostRequest.Xml(GelbooruPostFilter.ById(postId(1)))
        val response = XmlGelbooruPostNetworkManager(HttpClient()).getPost(request)
        println(response)
    }
}

