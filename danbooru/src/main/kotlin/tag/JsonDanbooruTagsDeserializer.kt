package tag

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface DanbooruTagsDeserializer<out Tags : DanbooruTags<*>> {
    fun deserializeTags(string: String): Tags
}

class XmlDanbooruTagsDeserializer : DanbooruTagsDeserializer<XmlDanbooruTags> {

    override fun deserializeTags(string: String): XmlDanbooruTags {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        return XmlDanbooruTags(jsoup.getElementsByTag("tag").mapNotNull(::deserializeTag))
    }

    private val xmlTagDeserializer = XmlDanbooruTagDeserializer()
    private fun deserializeTag(element: Element): XmlDanbooruTag? = try {
        xmlTagDeserializer.deserializeTag(element.toString())
    } catch (vie: ValueInstantiationException) { // could not find one or more values in json
        null // skip
    }
}

class JsonDanbooruTagsDeserializer : DanbooruTagsDeserializer<JsonDanbooruTags> {

    override fun deserializeTags(string: String): JsonDanbooruTags {
        return JsonDanbooruTags(JsonMapper().readValue<JsonNode>(string).mapNotNull(::deserializeTag))
    }

    private val jsonTagDeserializer = JsonDanbooruTagDeserializer()
    private fun deserializeTag(element: JsonNode): JsonDanbooruTag? = try {
        jsonTagDeserializer.deserializeTag(element.toString())
    } catch (vie: ValueInstantiationException) { // could not find one or more values in json
        null // skip
    }
}