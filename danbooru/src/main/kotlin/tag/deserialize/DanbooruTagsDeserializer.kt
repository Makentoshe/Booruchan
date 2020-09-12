package tag.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import post.deserialize.XmlDanbooruPostDeserialize
import tag.*
import tag.network.DanbooruTagsResponse
import tag.network.JsonDanbooruTagResponse
import tag.network.XmlDanbooruTagResponse

interface DanbooruTagsDeserializer {
    fun deserializeTags(response: DanbooruTagsResponse.Success): DanbooruTagsDeserialize<*>
}

class XmlDanbooruTagsDeserializer : DanbooruTagsDeserializer {

    override fun deserializeTags(response: DanbooruTagsResponse.Success): XmlDanbooruTagsDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlDanbooruTagsDeserialize(jsoup.getElementsByTag("tag").map(::deserializeTag))
    }

    private val xmlTagDeserializer = XmlDanbooruTagDeserializer()
    private fun deserializeTag(element: Element): XmlDanbooruTagDeserialize = try {
        xmlTagDeserializer.deserializeTag(XmlDanbooruTagResponse.Success(element.toString()))
    } catch (vie: ValueInstantiationException) { // could not find one or more values in json
        XmlDanbooruTagDeserialize.Failure(emptyMap()) //todo
    }
}

class JsonDanbooruTagsDeserializer : DanbooruTagsDeserializer {

    override fun deserializeTags(response: DanbooruTagsResponse.Success): JsonDanbooruTagsDeserialize {
        return JsonDanbooruTagsDeserialize(JsonMapper().readValue<JsonNode>(response.string).map(::deserializeTag))
    }

    private val jsonTagDeserializer = JsonDanbooruTagDeserializer()
    private fun deserializeTag(element: JsonNode): JsonDanbooruTagDeserialize = try {
        jsonTagDeserializer.deserializeTag(JsonDanbooruTagResponse.Success(element.toString()))
    } catch (vie: ValueInstantiationException) { // could not find one or more values in json
        JsonDanbooruTagDeserialize.Failure(emptyMap()) //todo
    }
}