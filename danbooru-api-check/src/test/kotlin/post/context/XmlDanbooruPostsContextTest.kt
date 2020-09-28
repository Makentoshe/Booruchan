package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.network.DanbooruPostsFilter
import post.network.DanbooruPostsNetworkManager
import post.network.XmlDanbooruPostsRequest
import java.util.logging.Logger

class XmlDanbooruPostsContextTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml posts`() = runBlocking {
        val context = XmlDanbooruPostsContext { DanbooruPostsNetworkManager(HttpClient()).getPosts(it) }

        val request = XmlDanbooruPostsRequest(DanbooruPostsFilter(count = 5))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.xml?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}
