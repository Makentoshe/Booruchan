package comment.context

import comment.network.GelbooruCommentsFilter
import comment.network.GelbooruCommentsNetworkManager
import comment.network.XmlGelbooruCommentsRequest
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import post.postId

class XmlGelbooruCommentsContextTest : GelbooruCommentsContextTest() {

    override val context = XmlGelbooruCommentsContext { GelbooruCommentsNetworkManager(HttpClient()).getComments(it) }

    @Test
    fun `should request xml comments`() = runBlocking {
        val request = XmlGelbooruCommentsRequest(GelbooruCommentsFilter(postId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=comment&q=index&post_id=1", request.url)

        val result = context.get(request)
        logger.info { "Result: $result" }
        val successResult = context.get(request).getOrNull()!!
        assert(successResult.deserializes.size >= 111)
        successResult.comments.forEach { assertEquals(1, it.postId) }
    }
}
