package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.JsonDanbooruTagsDeserializer
import tag.network.DanbooruTagsFilter
import tag.network.DanbooruTagsRequest
import tag.network.JsonDanbooruTagsNetworkManager
import tag.network.JsonDanbooruTagsResponse

class JsonDanbooruTagsNetworkManagerTest {

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tags with count param`() = runBlocking {
        val request = DanbooruTagsRequest.Json(DanbooruTagsFilter(count = 20))
        val response = JsonDanbooruTagsNetworkManager(HttpClient()).getTags(request) as JsonDanbooruTagsResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val tags = JsonDanbooruTagsDeserializer().deserializeTags(response)
        assertEquals(20, tags.tags.size)
    }
}
