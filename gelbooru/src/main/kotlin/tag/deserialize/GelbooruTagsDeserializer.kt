package tag.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import tag.network.*

interface GelbooruTagsDeserializer<in Response : GelbooruTagsResponse.Success> {
    fun deserializeTags(response: Response): GelbooruTagsDeserialize
}

class XmlGelbooruTagsDeserializer : GelbooruTagsDeserializer<XmlGelbooruTagsResponse.Success> {

    override fun deserializeTags(response: XmlGelbooruTagsResponse.Success): XmlGelbooruTagsDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlGelbooruTagsDeserialize(jsoup.getElementsByTag("tag").map(::deserializeTag))
    }

    private val xmlTagDeserializer = XmlGelbooruTagDeserializer()
    private fun deserializeTag(element: Element): XmlGelbooruTagDeserialize =
        xmlTagDeserializer.deserializeTag(XmlGelbooruTagResponse.Success(element.toString()))
}

class JsonGelbooruTagsDeserializer : GelbooruTagsDeserializer<JsonGelbooruTagsResponse.Success> {

    override fun deserializeTags(response: JsonGelbooruTagsResponse.Success): JsonGelbooruTagsDeserialize {
        return JsonGelbooruTagsDeserialize(JsonMapper().readValue<JsonNode>(response.string).map(::deserializeTag))
    }

    private val jsonTagDeserializer = JsonGelbooruTagDeserializer()
    private fun deserializeTag(element: JsonNode): JsonGelbooruTagDeserialize =
        jsonTagDeserializer.deserializeTag(JsonGelbooruTagResponse.Success(element.toString()))
}