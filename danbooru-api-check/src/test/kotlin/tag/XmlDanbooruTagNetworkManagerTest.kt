package tag

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.XmlDanbooruTagDeserialize
import tag.deserialize.XmlDanbooruTagDeserializer
import tag.entity.tagId
import tag.network.DanbooruTagFilter
import tag.network.DanbooruTagRequest
import tag.network.XmlDanbooruTagNetworkManager
import tag.network.XmlDanbooruTagResponse
import java.util.logging.Logger

class XmlDanbooruTagNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml tag by id`() = runBlocking {
        val request = DanbooruTagRequest.Xml(DanbooruTagFilter.ById(tagId(385430)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags/385430.xml", request.url)
        val response = XmlDanbooruTagNetworkManager(HttpClient()).getTag(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlDanbooruTagResponse.Success

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlDanbooruTagDeserializer().deserializeTag(successResponse)
        logger.info { "Deserialize: $deserialize" }
        deserialize as XmlDanbooruTagDeserialize.Success
        assertEquals(385430, deserialize.tag.tagId)
    }
}
