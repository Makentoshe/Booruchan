package comment

import comment.deserialize.JsonDanbooruCommentDeserialize
import comment.deserialize.JsonDanbooruCommentDeserializer
import comment.network.DanbooruCommentFilter
import comment.network.DanbooruCommentRequest
import comment.network.JsonDanbooruCommentNetworkManager
import comment.network.JsonDanbooruCommentResponse
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class JsonDanbooruCommentNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json comment by id`() = runBlocking {
        val request = DanbooruCommentRequest.Json(DanbooruCommentFilter.ById(commentId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments/1.json", request.url)
        val response = JsonDanbooruCommentNetworkManager(HttpClient()).getComment(request)
        logger.info { "Response: $response" }
        val successResponse = response as JsonDanbooruCommentResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonDanbooruCommentDeserializer().deserializeComment(successResponse)
        deserialize as JsonDanbooruCommentDeserialize.Success
        assertEquals(1, deserialize.comment.commentId)
        logger.info { "Comment: ${deserialize.comment}" }
    }
}
