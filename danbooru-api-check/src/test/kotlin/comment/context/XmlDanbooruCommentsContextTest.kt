package comment.context

import comment.network.DanbooruCommentsFilter
import comment.network.DanbooruCommentsNetworkManager
import comment.network.XmlDanbooruCommentsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import java.util.logging.Logger

class XmlDanbooruCommentsContextTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml comments`() = runBlocking {
        val context = XmlDanbooruCommentsContext { DanbooruCommentsNetworkManager(HttpClient()).getComments(it) }

        val request = XmlDanbooruCommentsRequest(DanbooruCommentsFilter(count = 5))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.xml?limit=5&group_by=comment", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
