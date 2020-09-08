package network

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
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
            enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
        }.readTree(response.string)

        return@runBlocking
    }
}
