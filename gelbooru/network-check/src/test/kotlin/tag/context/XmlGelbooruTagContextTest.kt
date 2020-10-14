package tag.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import tag.network.GelbooruTagFilter
import tag.network.GelbooruTagNetworkManager
import tag.network.XmlGelbooruTagRequest
import tag.tagId

class XmlGelbooruTagContextTest : GelbooruTagContextTest() {

    override val context = XmlGelbooruTagContext { GelbooruTagNetworkManager(HttpClient()).getTag(it) }

    @Test
    fun `should request xml tag`() = runBlocking {
        val request = XmlGelbooruTagRequest(GelbooruTagFilter.ById(tagId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=tag&q=index&id=1", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(1, successResult.tag.tagId)
    }

}
