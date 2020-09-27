package comment

import comment.deserialize.JsonDanbooruCommentsDeserializer
import comment.deserialize.XmlDanbooruCommentsDeserializer
import comment.network.DanbooruCommentsFilter
import comment.network.DanbooruCommentsNetworkManager
import comment.network.JsonDanbooruCommentsRequest
import comment.network.XmlDanbooruCommentsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class DanbooruCommentsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tags with count param`() = runBlocking {
        val request = JsonDanbooruCommentsRequest(DanbooruCommentsFilter(count = 20))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.json?limit=20&group_by=comment", request.url)
        val result = DanbooruCommentsNetworkManager(HttpClient()).getComments(request)
        logger.info { "Response: $result" }

        // deserialize json and check: was the filter condition satisfied?
        val comments = JsonDanbooruCommentsDeserializer().deserializeComments(result.getOrNull()!!)
        assertEquals(20, comments.deserializes.size)
        logger.info { "Success: ${comments.comments.size}" }
        logger.info { "Failure: ${comments.failures.size}" }
    }

    @Test
    fun `should request xml comments with count param`() = runBlocking {
        val request = XmlDanbooruCommentsRequest(DanbooruCommentsFilter(count = 20))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.xml?limit=20&group_by=comment", request.url)
        val result = DanbooruCommentsNetworkManager(HttpClient()).getComments(request)
        logger.info { "Response: $result" }

        // deserialize json and check: was the filter condition satisfied?
        val comments = XmlDanbooruCommentsDeserializer().deserializeComments(result.getOrNull()!!)
        assertEquals(20, comments.comments.size)
        logger.info { "Success: ${comments.comments.size}" }
        logger.info { "Failure: ${comments.failures.size}" }
    }
}
