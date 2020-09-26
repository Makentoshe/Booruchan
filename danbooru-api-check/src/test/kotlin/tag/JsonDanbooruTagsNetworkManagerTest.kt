package tag

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.JsonDanbooruTagsDeserializer
import tag.network.DanbooruTagsFilter
import tag.network.DanbooruTagsNetworkManager
import tag.network.DanbooruTagsRequest
import java.util.logging.Logger

class JsonDanbooruTagsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tags with count param`() = runBlocking {
        val request = DanbooruTagsRequest.Json(DanbooruTagsFilter(count = 20))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags.json?limit=20", request.url)
        val response = DanbooruTagsNetworkManager(HttpClient()).getTags(request)
        logger.info { "Response: $response" }

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonDanbooruTagsDeserializer().deserializeTags(response.getOrNull()!!)
        assertEquals(20, deserialize.deserializes.size)
        logger.info { "Success: ${deserialize.tags.size}" }
        logger.info { "Failure: ${deserialize.failures.size}" }
    }
}
