package tag.network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.XmlGelbooruTagDeserialize
import tag.deserialize.XmlGelbooruTagDeserializer
import tag.entity.tagId
import java.util.logging.Logger

class XmlGelbooruTagNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    @Ignore()
    fun `should request xml tag by id`() = runBlocking {
        val request = XmlGelbooruTagRequest(GelbooruTagFilter.ById(tagId(3854)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com//index.php?page=dapi&s=tag&q=index&id=3854", request.url)
        val response = XmlGelbooruTagNetworkManager(HttpClient()).getTag(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlGelbooruTagResponse.Success

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(successResponse)
        logger.info { "Deserialize: $deserialize" }
        deserialize as XmlGelbooruTagDeserialize.Success
        assertEquals(3854, deserialize.tag.tagId)
    }
}
