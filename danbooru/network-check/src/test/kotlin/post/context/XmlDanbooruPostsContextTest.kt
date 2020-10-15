package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import post.network.DanbooruPostsFilter
import post.network.DanbooruPostsNetworkManager
import post.network.XmlDanbooruPostsRequest

class XmlDanbooruPostsContextTest: DanbooruPostsContextTest() {

    override val context = XmlDanbooruPostsContext { DanbooruPostsNetworkManager(HttpClient()).getPosts(it) }

    @Test
    fun `should request xml posts`() = runBlocking {
        val request = XmlDanbooruPostsRequest(DanbooruPostsFilter(count = 5))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts.xml?limit=5", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}