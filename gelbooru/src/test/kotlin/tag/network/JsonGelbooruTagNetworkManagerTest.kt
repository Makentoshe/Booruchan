package tag.network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.JsonGelbooruTagDeserialize
import tag.deserialize.JsonGelbooruTagDeserializer
import tag.tagId
import java.util.logging.Logger

class JsonGelbooruTagNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(10)

    @Test
    @Ignore()
    fun `should request json tag by id`() = runBlocking {
        val request = JsonGelbooruTagRequest(GelbooruTagFilter.ById(tagId(3854)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com//index.php?page=dapi&s=tag&q=index&id=3854&json=1", request.url)
        val response = JsonGelbooruTagNetworkManager(HttpClient()).getTag(request)
        logger.info { "Response: $response" }
        val successResponse = response as JsonGelbooruTagResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(successResponse)
        logger.info { "Deserialize: $deserialize" }
        deserialize as JsonGelbooruTagDeserialize.Success
        assertEquals(3854, deserialize.tag.tagId)
    }
}
