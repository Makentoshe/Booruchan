package comment

import comment.deserialize.JsonDanbooruCommentsDeserializer
import comment.network.DanbooruCommentsFilter
import comment.network.DanbooruCommentsRequest
import comment.network.JsonDanbooruCommentsNetworkManager
import comment.network.JsonDanbooruCommentsResponse
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class JsonDanbooruCommentsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tags with count param`() = runBlocking {
        val request = DanbooruCommentsRequest.Json(DanbooruCommentsFilter(count = 20))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.json?limit=20&group_by=comment", request.url)
        val response = JsonDanbooruCommentsNetworkManager(HttpClient()).getComments(request)
        logger.info { "Response: $response" }
        val successResponse = response as JsonDanbooruCommentsResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val comments = JsonDanbooruCommentsDeserializer().deserializeComments(successResponse)
        assertEquals(20, comments.deserializes.size)
        logger.info { "Success: ${comments.comments.size}" }
        logger.info { "Failure: ${comments.failures.size}" }
    }
}
