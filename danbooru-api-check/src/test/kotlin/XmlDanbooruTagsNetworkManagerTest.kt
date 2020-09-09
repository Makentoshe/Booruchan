package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.XmlDanbooruTagsDeserializer
import tag.network.DanbooruTagsFilter
import tag.network.DanbooruTagsRequest
import tag.network.DanbooruTagsResponse
import tag.network.XmlDanbooruTagsNetworkManager

class XmlDanbooruTagsNetworkManagerTest {

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml tags with count param`() = runBlocking {
        val request = DanbooruTagsRequest.Xml(DanbooruTagsFilter(count = 20))
        val response = XmlDanbooruTagsNetworkManager(HttpClient()).getTags(request) as DanbooruTagsResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val tags = XmlDanbooruTagsDeserializer().deserializeTags(response)
        Assert.assertEquals(20, tags.tags.size)
    }
}