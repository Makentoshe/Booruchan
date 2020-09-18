package tag

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.XmlDanbooruTagsDeserializer
import tag.network.DanbooruTagsFilter
import tag.network.DanbooruTagsRequest
import tag.network.XmlDanbooruTagsNetworkManager
import tag.network.XmlDanbooruTagsResponse
import java.util.logging.Logger

class XmlDanbooruTagsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml tags with count param`() = runBlocking {
        val request = DanbooruTagsRequest.Xml(DanbooruTagsFilter(count = 20))
        logger.info { "Xml url request: ${request.url}" }
        Assert.assertEquals("https://danbooru.donmai.us/tags.xml?limit=20", request.url)
        val response = XmlDanbooruTagsNetworkManager(HttpClient()).getTags(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlDanbooruTagsResponse.Success

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlDanbooruTagsDeserializer().deserializeTags(successResponse)
        Assert.assertEquals(20, deserialize.deserializes.size)
        logger.info { "Success: ${deserialize.tags.size}" }
        logger.info { "Failure: ${deserialize.failures.size}" }
    }
}