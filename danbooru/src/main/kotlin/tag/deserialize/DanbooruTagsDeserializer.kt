package tag.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import tag.network.*

interface DanbooruTagsDeserializer<in Response : DanbooruTagsResponse.Success> {
    fun deserializeTags(response: Response): DanbooruTagsDeserialize
}

class XmlDanbooruTagsDeserializer : DanbooruTagsDeserializer<XmlDanbooruTagsResponse.Success> {

    override fun deserializeTags(response: XmlDanbooruTagsResponse.Success): XmlDanbooruTagsDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlDanbooruTagsDeserialize(jsoup.getElementsByTag("tag").map(::deserializeTag))
    }

    private val xmlTagDeserializer = XmlDanbooruTagDeserializer()
    private fun deserializeTag(element: Element): XmlDanbooruTagDeserialize =
        xmlTagDeserializer.deserializeTag(XmlDanbooruTagResponse.Success(element.toString()))
}

class JsonDanbooruTagsDeserializer : DanbooruTagsDeserializer<JsonDanbooruTagsResponse.Success> {

    override fun deserializeTags(response: JsonDanbooruTagsResponse.Success): JsonDanbooruTagsDeserialize {
        return JsonDanbooruTagsDeserialize(JsonMapper().readValue<JsonNode>(response.string).map(::deserializeTag))
    }

    private val jsonTagDeserializer = JsonDanbooruTagDeserializer()
    private fun deserializeTag(element: JsonNode): JsonDanbooruTagDeserialize =
        jsonTagDeserializer.deserializeTag(JsonDanbooruTagResponse.Success(element.toString()))
}