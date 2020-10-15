package comment.network

import comment.deserialize.XmlGelbooruCommentsDeserializer
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.postId
import java.util.logging.Logger

class XmlGelbooruCommentsNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    @Ignore
    fun `should request xml with post_id param`() = runBlocking {
        val request = XmlGelbooruCommentsRequest(GelbooruCommentsFilter(postId = postId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=comment&q=index&post_id=1", request.url)
        val response = XmlGelbooruCommentsNetworkManager(HttpClient()).getComments(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlGelbooruCommentsResponse.Success

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlGelbooruCommentsDeserializer().deserializeComments(successResponse.string)
        val successDeserialize = deserialize.getOrNull()!!
        successDeserialize.comments.forEach { assertEquals(1, it.postId) }
        assert(successDeserialize.deserializes.size >= 111)
        logger.info { "Success: ${successDeserialize.comments.size}" }
        logger.info { "Failure: ${successDeserialize.failures.size}" }
    }
}