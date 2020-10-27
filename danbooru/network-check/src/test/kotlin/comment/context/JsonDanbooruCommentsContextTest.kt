package comment.context

import comment.network.DanbooruCommentsFilter
import DanbooruCommentsNetworkManager
import comment.network.JsonDanbooruCommentsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruCommentsContextTest: DanbooruCommentsContextTest() {

    override val context = JsonDanbooruCommentsContext { DanbooruCommentsNetworkManager(HttpClient()).getComments(it) }

    @Test
    fun `should request json comments`() = runBlocking {
        val request = JsonDanbooruCommentsRequest(DanbooruCommentsFilter(count = 5))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/comments.json?limit=5&group_by=comment", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(5, successResult.deserializes.size)
    }
}