package comment

import comment.deserialize.JsonDanbooruCommentDeserializer
import comment.deserialize.XmlDanbooruCommentDeserializer
import comment.network.DanbooruCommentFilter
import DanbooruCommentNetworkManager
import comment.network.JsonDanbooruCommentRequest
import comment.network.XmlDanbooruCommentRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class DanbooruCommentNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json comment by id`() = runBlocking {
        val request = JsonDanbooruCommentRequest(DanbooruCommentFilter.ById(commentId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments/1.json", request.url)

        val networkResult = DanbooruCommentNetworkManager(HttpClient()).getComment(request)
        logger.info { "Network result: $networkResult" }
        val networkSuccess = networkResult.getOrNull()!!

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = JsonDanbooruCommentDeserializer().deserializeComment(networkSuccess)
        logger.info { "Deserialize result: $deserializeResult" }
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(1, deserializeSuccess.comment.commentId)
        logger.info { "Comment: ${deserializeSuccess.comment}" }
    }

    @Test
    fun `should request xml comment by id`() = runBlocking {
        val request = XmlDanbooruCommentRequest(DanbooruCommentFilter.ById(commentId(38543)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments/38543.xml", request.url)

        val networkResult = DanbooruCommentNetworkManager(HttpClient()).getComment(request)
        logger.info { "Network result: $networkResult" }
        val networkSuccess = networkResult.getOrNull()!!

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = XmlDanbooruCommentDeserializer().deserializeComment(networkSuccess)
        logger.info { "Deserialize result: $deserializeResult" }
        val deserializeSuccess = deserializeResult.getOrNull()!!

        assertEquals(38543, deserializeSuccess.comment.commentId)
        logger.info { "Comment: ${deserializeSuccess.comment}" }
    }
}
