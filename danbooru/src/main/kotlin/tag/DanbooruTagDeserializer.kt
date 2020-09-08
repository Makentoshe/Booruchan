package tag

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import tag.network.DanbooruTagResponse

interface DanbooruTagDeserializer<out Tag : DanbooruTag> {
    fun deserializeTag(response: DanbooruTagResponse.Success): Tag
}

class XmlDanbooruTagDeserializer : DanbooruTagDeserializer<XmlDanbooruTag> {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(response: DanbooruTagResponse.Success): XmlDanbooruTag {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        return mapper.readValue(jsoup.children().toString().replace("\\s".toRegex(), ""))
    }
}

class JsonDanbooruTagDeserializer : DanbooruTagDeserializer<JsonDanbooruTag> {

    private val mapper = JsonMapper()

    override fun deserializeTag(response: DanbooruTagResponse.Success): JsonDanbooruTag {
        return mapper.readValue(response.string)
    }
}