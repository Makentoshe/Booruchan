package tag.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import tag.network.DanbooruTagsFilter
import tag.network.DanbooruTagsNetworkManager
import tag.network.JsonDanbooruTagsRequest

class JsonDanbooruTagsContextTest: DanbooruTagsContextTest() {

    override val context = JsonDanbooruTagsContext { DanbooruTagsNetworkManager(HttpClient()).getTags(it) }

    @Test
    fun `should request json tags`() = runBlocking {
        val request = JsonDanbooruTagsRequest(DanbooruTagsFilter(count = 5))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/tags.json?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}