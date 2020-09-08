package network

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
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

        // throws JsonProcessingException on duplicate key or other error
        XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory()).apply {
            registerModules(KotlinModule(), JacksonXmlModule())
            enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY)
        }.readTree(response.string)

        return@runBlocking
    }
}
