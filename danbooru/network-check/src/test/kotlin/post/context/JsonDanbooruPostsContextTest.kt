package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import post.network.DanbooruPostsFilter
import DanbooruPostsNetworkManager
import post.network.JsonDanbooruPostsRequest

class JsonDanbooruPostsContextTest: DanbooruPostsContextTest() {

    override val context = JsonDanbooruPostsContext { DanbooruPostsNetworkManager(HttpClient()).getPosts(it) }

    @Test
    fun `should request json posts`() = runBlocking {
        val request = JsonDanbooruPostsRequest(DanbooruPostsFilter(count = 5))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.json?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}