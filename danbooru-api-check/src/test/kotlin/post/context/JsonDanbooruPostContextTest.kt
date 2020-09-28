package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.network.DanbooruPostFilter
import post.network.DanbooruPostNetworkManager
import post.network.JsonDanbooruPostRequest
import post.postId
import java.util.logging.Logger

// todo add request post with id 2
class JsonDanbooruPostContextTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json post`() = runBlocking {
        val context = JsonDanbooruPostContext { DanbooruPostNetworkManager(HttpClient()).getPost(it) }

        val request = JsonDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.json", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = result.getOrNull()!!

        assertEquals(1, successResult.post.postId)
    }

}