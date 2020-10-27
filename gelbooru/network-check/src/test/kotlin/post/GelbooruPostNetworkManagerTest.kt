package post

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.deserialize.JsonGelbooruPostDeserializer
import post.deserialize.XmlGelbooruPostDeserializer
import post.network.GelbooruPostFilter
import GelbooruPostNetworkManager
import post.network.JsonGelbooruPostRequest
import post.network.XmlGelbooruPostRequest
import java.util.logging.Logger

class GelbooruPostNetworkManagerTest {

    private val logger = Logger.getLogger(this.javaClass.simpleName)

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json post`() = runBlocking {
        val request = JsonGelbooruPostRequest(GelbooruPostFilter.ById(postId(1)))
        logger.info { "Json url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=post&q=index&id=1&json=1", request.url)
        val response = GelbooruPostNetworkManager(HttpClient()).getPost(request)
        logger.info { "Response: $response" }

        // deserialize json and check: was the filter condition satisfied?
        val deserializeResult = JsonGelbooruPostDeserializer().deserializePost(response.getOrNull()!!)
        val deserializeSuccess = deserializeResult.getOrNull()!!
        assertEquals(1, deserializeSuccess.post.postId)
        logger.info { "Post: ${deserializeSuccess.post}" }
    }

    @Test
    fun `should request xml post`() = runBlocking {
        val request = XmlGelbooruPostRequest(GelbooruPostFilter.ById(postId(1)))
        logger.info { "Xml url request: ${request.url}" }
        assertEquals("https://gelbooru.com/index.php?page=dapi&s=post&q=index&id=1", request.url)
        val response = GelbooruPostNetworkManager(HttpClient()).getPost(request)
        logger.info { "Response: $response" }

        // deserialize xml and check: was the filter condition satisfied?
        val deserializeResult = XmlGelbooruPostDeserializer().deserializePost(response.getOrNull()!!)
        val deserializeSuccess = deserializeResult.getOrNull()!!
        assertEquals(1, deserializeSuccess.post.postId)
        logger.info { "Post: ${deserializeSuccess.post}" }
    }

}