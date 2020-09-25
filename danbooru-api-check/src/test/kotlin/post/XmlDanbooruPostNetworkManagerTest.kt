package post

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.deserialize.XmlDanbooruPostDeserialize
import post.deserialize.XmlDanbooruPostDeserializer
import post.network.DanbooruPostFilter
import post.network.XmlDanbooruPostNetworkManager
import post.network.XmlDanbooruPostRequest
import post.network.XmlDanbooruPostResponse
import java.util.logging.Logger

class XmlDanbooruPostNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml post`() = runBlocking {
        val request = XmlDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.xml", request.url)
        val response = XmlDanbooruPostNetworkManager(HttpClient()).getPost(request)
        logger.info { "Response: $response" }
        val successResponse = response as XmlDanbooruPostResponse.Success

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlDanbooruPostDeserializer().deserializePost(successResponse)
        val successDeserialize = deserialize as XmlDanbooruPostDeserialize.Success
        assertEquals(1, successDeserialize.post.postId)
        logger.info { "Post: ${successDeserialize.post}" }
    }
}

