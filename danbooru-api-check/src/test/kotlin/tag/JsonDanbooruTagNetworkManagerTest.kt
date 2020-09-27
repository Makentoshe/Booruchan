package tag

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.JsonDanbooruTagDeserialize
import tag.deserialize.JsonDanbooruTagDeserializer
import tag.network.DanbooruTagFilter
import tag.network.DanbooruTagNetworkManager
import tag.network.JsonDanbooruTagRequest
import java.util.logging.Logger

class JsonDanbooruTagNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tag by id`() = runBlocking {
        val request = JsonDanbooruTagRequest(DanbooruTagFilter.ById(tagId(385430)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags/385430.json", request.url)
        val response = DanbooruTagNetworkManager(HttpClient()).getTag(request)
        logger.info { "Response: $response" }

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonDanbooruTagDeserializer().deserializeTag(response.getOrNull()!!)
        logger.info { "Deserialize: $deserialize" }
        deserialize as JsonDanbooruTagDeserialize.Success
        assertEquals(385430, deserialize.tag.tagId)
    }
}
