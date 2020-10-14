package tag.network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.XmlGelbooruTagsDeserializer
import java.util.logging.Logger

class XmlGelbooruTagsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    @Ignore
    fun `should request tags with count param`() = runBlocking {
        val request = XmlGelbooruTagsRequest(GelbooruTagsFilter(count = 20))
        logger.info { "Xml url request: ${request.url}" }
        Assert.assertEquals("https://gelbooru.com//index.php?page=dapi&s=tag&q=index&limit=20", request.url)
        val response = XmlGelbooruTagsNetworkManager(HttpClient()).getTags(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlGelbooruTagsResponse.Success

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlGelbooruTagsDeserializer().deserializeTags(successResponse.string)
        val successDeserialize = deserialize.getOrNull()!!
        Assert.assertEquals(20, successDeserialize.deserializes.size)
        logger.info { "Success: ${successDeserialize.tags.size}" }
        logger.info { "Failure: ${successDeserialize.failures.size}" }
    }
}