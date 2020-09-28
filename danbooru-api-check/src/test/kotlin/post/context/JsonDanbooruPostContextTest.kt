package post.context

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import post.network.DanbooruPostFilter
import post.network.DanbooruPostNetworkManager
import post.network.JsonDanbooruPostRequest
import post.postId

// todo add request post with id 2
class JsonDanbooruPostContextTest {

    @Test
    fun `should request json post`() = runBlocking {
        val request = JsonDanbooruPostRequest(DanbooruPostFilter.ById(postId(1)))
        val context = JsonDanbooruPostContext { DanbooruPostNetworkManager(HttpClient()).getPost(it) }
        val successResult = context.get(request).getOrNull()!!

        assertEquals(1, successResult.post.postId)
    }

}