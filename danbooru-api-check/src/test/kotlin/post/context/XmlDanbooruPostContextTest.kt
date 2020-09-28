package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import post.network.DanbooruPostFilter
import post.network.DanbooruPostNetworkManager
import post.network.XmlDanbooruPostRequest
import post.postId

// todo add request post with id 2
class XmlDanbooruPostContextTest {

    @Test
    fun `should request xml post`() = runBlocking {
        val request = XmlDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        val context = XmlDanbooruPostContext { DanbooruPostNetworkManager(HttpClient()).getPost(it) }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(1, successResult.post.postId)
    }

}
