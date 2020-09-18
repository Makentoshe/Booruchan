package comment

import comment.deserialize.XmlDanbooruCommentsDeserializer
import comment.network.DanbooruCommentsFilter
import comment.network.DanbooruCommentsRequest
import comment.network.XmlDanbooruCommentsNetworkManager
import comment.network.XmlDanbooruCommentsResponse
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class XmlDanbooruCommentsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml comments with count param`() = runBlocking {
        val request = DanbooruCommentsRequest.Xml(DanbooruCommentsFilter(count = 20))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.xml?limit=20&group_by=comment", request.url)
        val response = XmlDanbooruCommentsNetworkManager(HttpClient()).getComments(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlDanbooruCommentsResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val comments = XmlDanbooruCommentsDeserializer().deserializeComments(successResponse)
        assertEquals(20, comments.comments.size)
        logger.info { "Success: ${comments.comments.size}" }
        logger.info { "Failure: ${comments.failures.size}" }
    }
}