package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import tag.entity.tagId
import tag.network.DanbooruTagRequest
import tag.network.JsonDanbooruTagNetworkManager

class JsonDanbooruTagNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json tag`() = runBlocking {
        val request = DanbooruTagRequest.Json(tagId(385430))
        val response = JsonDanbooruTagNetworkManager(HttpClient()).getTag(request)
        println(response)
    }
}
