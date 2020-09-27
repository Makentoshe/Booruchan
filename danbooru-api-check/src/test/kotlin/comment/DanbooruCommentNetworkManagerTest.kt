package comment

import comment.deserialize.JsonDanbooruCommentDeserialize
import comment.deserialize.JsonDanbooruCommentDeserializer
import comment.deserialize.XmlDanbooruCommentDeserialize
import comment.deserialize.XmlDanbooruCommentDeserializer
import comment.network.DanbooruCommentFilter
import comment.network.DanbooruCommentNetworkManager
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
        val result = DanbooruCommentNetworkManager(HttpClient()).getComment(request)
        logger.info { "Response: $result" }

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonDanbooruCommentDeserializer().deserializeComment(result.getOrNull()!!)
        deserialize as JsonDanbooruCommentDeserialize.Success
        assertEquals(1, deserialize.comment.commentId)
        logger.info { "Comment: ${deserialize.comment}" }
    }

    @Test
    fun `should request xml comment by id`() = runBlocking {
        val request = XmlDanbooruCommentRequest(DanbooruCommentFilter.ById(commentId(38543)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments/38543.xml", request.url)
        val result = DanbooruCommentNetworkManager(HttpClient()).getComment(request)
        logger.info { "Response: $result" }

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = XmlDanbooruCommentDeserializer().deserializeComment(result.getOrNull()!!)
        val successDeserialize = deserialize as XmlDanbooruCommentDeserialize.Success
        assertEquals(38543, successDeserialize.comment.commentId)
        logger.info { "Comment: ${successDeserialize.comment}" }
    }
}
