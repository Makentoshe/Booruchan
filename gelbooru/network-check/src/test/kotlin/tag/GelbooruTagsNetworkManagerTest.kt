package tag

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.JsonGelbooruTagsDeserializer
import tag.deserialize.XmlGelbooruTagsDeserializer
import tag.network.GelbooruTagsFilter
import tag.network.GelbooruTagsNetworkManager
import tag.network.JsonGelbooruTagsRequest
import tag.network.XmlGelbooruTagsRequest
import java.util.logging.Logger

class GelbooruTagsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request tags with count param`() = runBlocking {
        val request = XmlGelbooruTagsRequest(GelbooruTagsFilter(count = 20))
        logger.info { "Xml url request: ${request.url}" }
        Assert.assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&limit=20", request.url)
        val response = GelbooruTagsNetworkManager(HttpClient()).getTags(request)
        logger.info { "Response: $response" }
        val successResponse = response.getOrNull()!!

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlGelbooruTagsDeserializer().deserializeTags(successResponse)
        val successDeserialize = deserialize.getOrNull()!!
        Assert.assertEquals(20, successDeserialize.deserializes.size)
        logger.info { "Success: ${successDeserialize.tags.size}" }
        logger.info { "Failure: ${successDeserialize.failures.size}" }
    }

    @Test
    fun `should request json tags with count param`() = runBlocking {
        val request = JsonGelbooruTagsRequest(GelbooruTagsFilter(count = 20))
        logger.info { "Json url request: ${request.url}" }
        Assert.assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&json=1&limit=20", request.url)
        val response = GelbooruTagsNetworkManager(HttpClient()).getTags(request)
        logger.info { "Response: $response" }
        val successResponse = response.getOrNull()!!

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonGelbooruTagsDeserializer().deserializeTags(successResponse)
        val successDeserialize = deserialize.getOrNull()!!
        Assert.assertEquals(20, successDeserialize.deserializes.size)
        logger.info { "Success: ${successDeserialize.tags.size}" }
        logger.info { "Failure: ${successDeserialize.failures.size}" }
    }
}