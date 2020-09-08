package network

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.JsonDanbooruTagDeserializer
import tag.entity.tagId
import tag.network.DanbooruTagFilter
import tag.network.DanbooruTagRequest
import tag.network.DanbooruTagResponse
import tag.network.JsonDanbooruTagNetworkManager

class JsonDanbooruTagNetworkManagerTest {

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request json tag by id`() = runBlocking {
        val request = DanbooruTagRequest.Json(DanbooruTagFilter.ById(tagId(385430)))
        val response = JsonDanbooruTagNetworkManager(HttpClient()).getTag(request) as DanbooruTagResponse.Success

        // throws JsonProcessingException on duplicate key or other error
        ObjectMapper().apply {
            // enable for single tag to be sure response is ok (it is not very well check but ok)
            enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
        }.readTree(response.string)

        // deserialize json and check: was the filter condition satisfied?
        val tag = JsonDanbooruTagDeserializer().deserializeTag(response)
        assertEquals(385430, tag.tagId)
    }
}
