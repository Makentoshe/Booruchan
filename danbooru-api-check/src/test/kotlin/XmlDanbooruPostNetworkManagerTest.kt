package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.XmlDanbooruPostDeserializer
import post.network.DanbooruPostFilter
import post.network.DanbooruPostRequest
import post.network.XmlDanbooruPostNetworkManager
import post.network.XmlDanbooruPostResponse
import post.postId

class XmlDanbooruPostNetworkManagerTest {

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml post`() = runBlocking {
        val request = DanbooruPostRequest.Xml(DanbooruPostFilter.ById(postId(1)))
        val response = XmlDanbooruPostNetworkManager(HttpClient()).getPost(request)

        // deserialize xml and check: was the filter condition satisfied?
        val post = XmlDanbooruPostDeserializer().deserializePost(response as XmlDanbooruPostResponse.Success)
        assertEquals(1, post.postId)
    }
}

