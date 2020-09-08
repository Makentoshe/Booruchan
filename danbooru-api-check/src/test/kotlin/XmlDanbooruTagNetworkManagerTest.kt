package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import tag.entity.tagId
import tag.network.DanbooruTagFilter
import tag.network.DanbooruTagRequest
import tag.network.XmlDanbooruTagNetworkManager

class XmlDanbooruTagNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json tag`() = runBlocking {
        val request = DanbooruTagRequest.Xml(DanbooruTagFilter.ById(tagId(385430)))
        val response = XmlDanbooruTagNetworkManager(HttpClient()).getTag(request)
        println(response)
    }
}
