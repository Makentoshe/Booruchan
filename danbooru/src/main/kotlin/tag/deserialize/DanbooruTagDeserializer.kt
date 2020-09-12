package tag.deserialize

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
import tag.DanbooruTag
import tag.JsonDanbooruTag
import tag.XmlDanbooruTag
import tag.network.DanbooruTagResponse

interface DanbooruTagDeserializer {
    fun deserializeTag(response: DanbooruTagResponse.Success): DanbooruTagDeserialize
}

class XmlDanbooruTagDeserializer : DanbooruTagDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(response: DanbooruTagResponse.Success): XmlDanbooruTagDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        val tag = mapper.readValue<XmlDanbooruTag>(jsoup.children().toString().replace("\\s".toRegex(), ""))
        return XmlDanbooruTagDeserialize.Success(tag) // todo add failure
    }
}

class JsonDanbooruTagDeserializer : DanbooruTagDeserializer {

    private val mapper = JsonMapper()

    override fun deserializeTag(response: DanbooruTagResponse.Success): JsonDanbooruTagDeserialize {
        val tag = mapper.readValue<JsonDanbooruTag>(response.string)
        return JsonDanbooruTagDeserialize.Success(tag) // todo add failure
    }
}