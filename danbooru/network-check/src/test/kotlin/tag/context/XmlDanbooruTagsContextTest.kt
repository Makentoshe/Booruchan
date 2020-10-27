package tag.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import tag.network.DanbooruTagsFilter
import DanbooruTagsNetworkManager
import tag.network.XmlDanbooruTagsRequest

class XmlDanbooruTagsContextTest : DanbooruTagsContextTest() {

    override val context = XmlDanbooruTagsContext { DanbooruTagsNetworkManager(HttpClient()).getTags(it) }

    @Test
    fun `should request xml tags`() = runBlocking {

        val request = XmlDanbooruTagsRequest(DanbooruTagsFilter(count = 5))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags.xml?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
