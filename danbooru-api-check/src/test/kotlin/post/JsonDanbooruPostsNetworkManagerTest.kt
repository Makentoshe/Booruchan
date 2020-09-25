package post

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.deserialize.JsonDanbooruPostsDeserializer
import post.network.DanbooruPostsFilter
import post.network.JsonDanbooruPostsNetworkManager
import post.network.JsonDanbooruPostsRequest
import post.network.JsonDanbooruPostsResponse
import java.util.logging.Logger

class JsonDanbooruPostsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json posts`() = runBlocking {
        val request = JsonDanbooruPostsRequest(DanbooruPostsFilter(count = 10))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.json?limit=10", request.url)
        val response = JsonDanbooruPostsNetworkManager(HttpClient()).getPosts(request)
        logger.info { "Response: $response" }
        val successResponse = response as JsonDanbooruPostsResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonDanbooruPostsDeserializer().deserializePosts(successResponse)
        assertEquals(10, deserialize.deserializes.size)
        logger.info { "Success: ${deserialize.posts.size}" }
        logger.info { "Failure: ${deserialize.failures.size}" }
    }
}