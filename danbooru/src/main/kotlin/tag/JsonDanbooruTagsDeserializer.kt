package tag

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import tag.network.DanbooruTagsResponse
import tag.network.JsonDanbooruTagResponse
import tag.network.XmlDanbooruTagResponse

interface DanbooruTagsDeserializer<out Tags : DanbooruTags<*>> {
    fun deserializeTags(response: DanbooruTagsResponse.Success): Tags
}

class XmlDanbooruTagsDeserializer : DanbooruTagsDeserializer<XmlDanbooruTags> {

    override fun deserializeTags(response: DanbooruTagsResponse.Success): XmlDanbooruTags {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlDanbooruTags(jsoup.getElementsByTag("tag").mapNotNull(::deserializeTag))
    }

    private val xmlTagDeserializer = XmlDanbooruTagDeserializer()
    private fun deserializeTag(element: Element): XmlDanbooruTag? = try {
        xmlTagDeserializer.deserializeTag(XmlDanbooruTagResponse.Success(element.toString()))
    } catch (vie: ValueInstantiationException) { // could not find one or more values in json
        null // skip
    }
}

class JsonDanbooruTagsDeserializer : DanbooruTagsDeserializer<JsonDanbooruTags> {

    override fun deserializeTags(response: DanbooruTagsResponse.Success): JsonDanbooruTags {
        return JsonDanbooruTags(JsonMapper().readValue<JsonNode>(response.string).mapNotNull(::deserializeTag))
    }

    private val jsonTagDeserializer = JsonDanbooruTagDeserializer()
    private fun deserializeTag(element: JsonNode): JsonDanbooruTag? = try {
        jsonTagDeserializer.deserializeTag(JsonDanbooruTagResponse.Success(element.toString()))
    } catch (vie: ValueInstantiationException) { // could not find one or more values in json
        null // skip
    }
}