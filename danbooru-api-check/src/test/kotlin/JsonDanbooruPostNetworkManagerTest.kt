package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import post.deserialize.JsonDanbooruPostDeserialize
import post.deserialize.JsonDanbooruPostDeserializer
import post.network.DanbooruPostFilter
import post.network.DanbooruPostRequest
import post.network.JsonDanbooruPostNetworkManager
import post.network.JsonDanbooruPostResponse
import post.postId

class JsonDanbooruPostNetworkManagerTest {

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json post`() = runBlocking {
        val request = DanbooruPostRequest.Json(DanbooruPostFilter.ById(postId(1)))
        val response = JsonDanbooruPostNetworkManager(HttpClient()).getPost(request)

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = JsonDanbooruPostDeserializer().deserializePost(response as JsonDanbooruPostResponse.Success)
        val successDeserialize = deserialize as JsonDanbooruPostDeserialize.Success
        assertEquals(1, successDeserialize.post.postId)
    }

    // todo add tests for id 2
}