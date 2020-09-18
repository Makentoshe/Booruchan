package post

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.deserialize.JsonDanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer
import post.network.DanbooruPostFilter
import post.network.DanbooruPostRequest
import post.network.JsonDanbooruPostNetworkManager
import post.network.JsonDanbooruPostResponse
import java.util.logging.Logger

class JsonDanbooruPostNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json post`() = runBlocking {
        val request = DanbooruPostRequest.Json(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.json", request.url)
        val response = JsonDanbooruPostNetworkManager(HttpClient()).getPost(request)
        logger.info { "Response: $response" }
        val successResponse = response as JsonDanbooruPostResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonDanbooruPostDeserializer().deserializePost(successResponse)
        val successDeserialize = deserialize as JsonDanbooruPostDeserialize.Success
        assertEquals(1, successDeserialize.post.postId)
        logger.info { "Post: ${deserialize.post}" }
    }

    // todo add tests for id 2
}