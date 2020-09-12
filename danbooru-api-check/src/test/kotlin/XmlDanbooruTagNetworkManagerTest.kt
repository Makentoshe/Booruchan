package network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import tag.deserialize.XmlDanbooruTagDeserialize
import tag.deserialize.XmlDanbooruTagDeserializer
import tag.entity.tagId
import tag.network.DanbooruTagFilter
import tag.network.DanbooruTagRequest
import tag.network.DanbooruTagResponse
import tag.network.XmlDanbooruTagNetworkManager

class XmlDanbooruTagNetworkManagerTest {

    @get:Rule
    val globalTimeout: Timeout = Timeout.seconds(30)

    @Test
    fun `should request xml tag by id`() = runBlocking {
        val request = DanbooruTagRequest.Xml(DanbooruTagFilter.ById(tagId(385430)))
        val response = XmlDanbooruTagNetworkManager(HttpClient()).getTag(request) as DanbooruTagResponse.Success

        // deserialize json and check: was the filter condition satisfied?
        val deserialize = XmlDanbooruTagDeserializer().deserializeTag(response)
        val successDeserialize = deserialize as XmlDanbooruTagDeserialize.Success
        assertEquals(385430, successDeserialize.tag.tagId)
    }
}
