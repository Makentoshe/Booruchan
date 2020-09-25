package post

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.deserialize.XmlDanbooruPostsDeserializer
import post.network.DanbooruPostsFilter
import post.network.XmlDanbooruPostsNetworkManager
import post.network.XmlDanbooruPostsRequest
import post.network.XmlDanbooruPostsResponse
import java.util.logging.Logger

class XmlDanbooruPostsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml posts`() = runBlocking {
        val request = XmlDanbooruPostsRequest(DanbooruPostsFilter(count = 10))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.xml?limit=10", request.url)
        val response = XmlDanbooruPostsNetworkManager(HttpClient()).getPosts(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlDanbooruPostsResponse.Success

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlDanbooruPostsDeserializer().deserializePosts(successResponse)
        assertEquals(10, deserialize.deserializes.size)
        logger.info { "Success: ${deserialize.posts.size}" }
        logger.info { "Failure: ${deserialize.failures.size}" }
    }
}

