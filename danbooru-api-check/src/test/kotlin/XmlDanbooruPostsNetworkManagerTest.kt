package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.XmlDanbooruPostsDeserializer
import post.network.DanbooruPostsFilter
import post.network.DanbooruPostsRequest
import post.network.XmlDanbooruPostsNetworkManager
import post.network.XmlDanbooruPostsResponse

class XmlDanbooruPostsNetworkManagerTest {

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml posts`() = runBlocking {
        val request = DanbooruPostsRequest.Xml(DanbooruPostsFilter(count = 10))
        val response = XmlDanbooruPostsNetworkManager(HttpClient()).getPosts(request)

        // deserialize xml and check: was the filter condition satisfied?
        val posts = XmlDanbooruPostsDeserializer().deserializePosts(response as XmlDanbooruPostsResponse.Success)
        assertEquals(10, posts.posts.size)
    }
}

