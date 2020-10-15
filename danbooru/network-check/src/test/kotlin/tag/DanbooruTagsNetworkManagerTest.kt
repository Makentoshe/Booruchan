package tag

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.JsonDanbooruTagsDeserializer
import tag.deserialize.XmlDanbooruTagsDeserializer
import tag.network.DanbooruTagsFilter
import tag.network.DanbooruTagsNetworkManager
import tag.network.JsonDanbooruTagsRequest
import tag.network.XmlDanbooruTagsRequest
import java.util.logging.Logger

class DanbooruTagsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tags with count param`() = runBlocking {
        val request = JsonDanbooruTagsRequest(DanbooruTagsFilter(count = 20))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags.json?limit=20", request.url)

        val response = DanbooruTagsNetworkManager(HttpClient()).getTags(request)
        logger.info { "Response: $response" }

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = JsonDanbooruTagsDeserializer().deserializeTags(response.getOrNull()!!)
        val deserializeSuccess = deserializeResult.getOrNull()!!
        assertEquals(20, deserializeSuccess.deserializes.size)

        logger.info { "Success: ${deserializeSuccess.tags.size}" }
        logger.info { "Failure: ${deserializeSuccess.failures.size}" }
    }

    @Test
    fun `should request xml tags with count param`() = runBlocking {
        val request = XmlDanbooruTagsRequest(DanbooruTagsFilter(count = 20))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags.xml?limit=20", request.url)

        val response = DanbooruTagsNetworkManager(HttpClient()).getTags(request)
        logger.info { "Response: $response" }

        // deserialize xml and check: was the filter condition satisfied?
        val deserializeResult = XmlDanbooruTagsDeserializer().deserializeTags(response.getOrNull()!!)
        val deserializeSuccess = deserializeResult.getOrNull()!!
        assertEquals(20, deserializeSuccess.deserializes.size)

        logger.info { "Success: ${deserializeSuccess.tags.size}" }
        logger.info { "Failure: ${deserializeSuccess.failures.size}" }
    }
}