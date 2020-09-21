package tag.network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.JsonGelbooruTagsDeserializer
import java.util.logging.Logger

class JsonGelbooruTagsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    @Ignore
    fun `should request json tags with count param`() = runBlocking {
        val request = JsonGelbooruTagsRequest(GelbooruTagsFilter(count = 20))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com//index.php?page=dapi&s=tag&q=index&json=1&limit=20", request.url)
        val response = JsonGelbooruTagsNetworkManager(HttpClient()).getTags(request)
        logger.info { "Response: $response" }
        val successResponse = response as JsonGelbooruTagsResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonGelbooruTagsDeserializer().deserializeTags(successResponse)
        assertEquals(20, deserialize.deserializes.size)
        logger.info { "Success: ${deserialize.tags.size}" }
        logger.info { "Failure: ${deserialize.failures.size}" }
    }
}
