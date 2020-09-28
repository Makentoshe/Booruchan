package tag.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.network.DanbooruTagFilter
import tag.network.DanbooruTagNetworkManager
import tag.network.JsonDanbooruTagRequest
import tag.tagId
import java.util.logging.Logger

class JsonDanbooruTagContextTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tag`() = runBlocking {
        val context = JsonDanbooruTagContext { DanbooruTagNetworkManager(HttpClient()).getTag(it) }

        val request = JsonDanbooruTagRequest(DanbooruTagFilter.ById(tagId(1598277)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags/1598277.json", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = result.getOrNull()!!

        assertEquals(1598277, successResult.tag.tagId)
    }

}