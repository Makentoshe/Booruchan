package network

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
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

        // throws JsonProcessingException on duplicate key or other error
        XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory()).apply {
            registerModules(KotlinModule(), JacksonXmlModule())
        }.readTree(response.string)

        // deserialize json and check: was the filter condition satisfied?
        val tags = XmlDanbooruTagsDeserializer().deserializeTags(response)
        Assert.assertEquals(20, tags.tags.size)
    }
}