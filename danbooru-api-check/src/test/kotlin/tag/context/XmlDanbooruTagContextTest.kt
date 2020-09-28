package tag.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.network.DanbooruTagFilter
import tag.network.DanbooruTagNetworkManager
import tag.network.XmlDanbooruTagRequest
import tag.tagId
import java.util.logging.Logger

class XmlDanbooruTagContextTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml tag`() = runBlocking {
        val context = XmlDanbooruTagContext { DanbooruTagNetworkManager(HttpClient()).getTag(it) }

        val request = XmlDanbooruTagRequest(DanbooruTagFilter.ById(tagId(1598277)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags/1598277.xml", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(1598277, successResult.tag.tagId)
    }

}
