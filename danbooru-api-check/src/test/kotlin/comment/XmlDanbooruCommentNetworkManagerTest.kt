package comment

import comment.deserialize.XmlDanbooruCommentDeserialize
import comment.deserialize.XmlDanbooruCommentDeserializer
import comment.network.DanbooruCommentFilter
import comment.network.DanbooruCommentRequest
import comment.network.XmlDanbooruCommentNetworkManager
import comment.network.XmlDanbooruCommentResponse
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class XmlDanbooruCommentNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml comment by id`() = runBlocking {
        val request = DanbooruCommentRequest.Xml(DanbooruCommentFilter.ById(commentId(38543)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments/38543.xml", request.url)
        val response = XmlDanbooruCommentNetworkManager(HttpClient()).getComment(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlDanbooruCommentResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = XmlDanbooruCommentDeserializer().deserializeComment(successResponse)
        val successDeserialize = deserialize as XmlDanbooruCommentDeserialize.Success
        assertEquals(38543, successDeserialize.comment.commentId)
        logger.info { "Comment: ${successDeserialize.comment}" }
    }
}
