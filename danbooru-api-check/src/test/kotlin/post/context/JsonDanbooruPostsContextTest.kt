package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.network.DanbooruPostsFilter
import post.network.DanbooruPostsNetworkManager
import post.network.JsonDanbooruPostsRequest
import java.util.logging.Logger

class JsonDanbooruPostsContextTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json posts`() = runBlocking {
        val context = JsonDanbooruPostsContext { DanbooruPostsNetworkManager(HttpClient()).getPosts(it) }

        val request = JsonDanbooruPostsRequest(DanbooruPostsFilter(count = 5))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.json?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}