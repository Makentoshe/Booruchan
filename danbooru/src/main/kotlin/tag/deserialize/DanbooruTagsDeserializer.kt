package tag.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface DanbooruTagsDeserializer {
    fun deserializeTags(string: String): DanbooruTagsDeserialize
}

class XmlDanbooruTagsDeserializer : DanbooruTagsDeserializer {

    override fun deserializeTags(string: String): XmlDanbooruTagsDeserialize {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        return XmlDanbooruTagsDeserialize(jsoup.getElementsByTag("tag").map(::deserializeTag))
    }

    private val xmlTagDeserializer = XmlDanbooruTagDeserializer()
    private fun deserializeTag(element: Element): XmlDanbooruTagDeserialize =
        xmlTagDeserializer.deserializeTag(element.toString())
}

class JsonDanbooruTagsDeserializer : DanbooruTagsDeserializer {

    override fun deserializeTags(string: String): JsonDanbooruTagsDeserialize {
        return JsonDanbooruTagsDeserialize(JsonMapper().readValue<JsonNode>(string).map(::deserializeTag))
    }

    private val jsonTagDeserializer = JsonDanbooruTagDeserializer()
    private fun deserializeTag(element: JsonNode): JsonDanbooruTagDeserialize =
        jsonTagDeserializer.deserializeTag(element.toString())
}