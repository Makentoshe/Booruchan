package post

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.deserialize.JsonDanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer
import post.deserialize.XmlDanbooruPostDeserialize
import post.deserialize.XmlDanbooruPostDeserializer
import post.network.DanbooruPostFilter
import post.network.DanbooruPostNetworkManager
import post.network.JsonDanbooruPostRequest
import post.network.XmlDanbooruPostRequest
import java.util.logging.Logger

class DanbooruPostNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json post`() = runBlocking {
        val request = JsonDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.json", request.url)
        val response = DanbooruPostNetworkManager(HttpClient()).getPost(request)
        logger.info { "Response: $response" }

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonDanbooruPostDeserializer().deserializePost(response.getOrNull()!!)
        val successDeserialize = deserialize as JsonDanbooruPostDeserialize.Success
        assertEquals(1, successDeserialize.post.postId)
        logger.info { "Post: ${deserialize.post}" }
    }

    @Test
    fun `should request xml post`() = runBlocking {
        val request = XmlDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://danbooru.donmai.us/posts/1.xml", request.url)
        val response = DanbooruPostNetworkManager(HttpClient()).getPost(request)
        logger.info { "Response: $response" }

        // deserialize xml and check: was the filter condition satisfied?
        val deserialize = XmlDanbooruPostDeserializer().deserializePost(response.getOrNull()!!)
        val successDeserialize = deserialize as XmlDanbooruPostDeserialize.Success
        assertEquals(1, successDeserialize.post.postId)
        logger.info { "Post: ${successDeserialize.post}" }
    }

    // todo add tests for id 2
}