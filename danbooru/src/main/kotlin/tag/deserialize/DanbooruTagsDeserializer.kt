package tag.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import deserialize.CollectionDeserializeException
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface DanbooruTagsDeserializer {
    fun deserializeTags(string: String): Result<DanbooruTagsDeserialize<*>>
}

class XmlDanbooruTagsDeserializer : DanbooruTagsDeserializer {

    override fun deserializeTags(string: String): Result<XmlDanbooruTagsDeserialize> = try {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        Result.success(XmlDanbooruTagsDeserialize(jsoup.getElementsByTag("tag").map(::deserializeTag)))
    } catch (exception: Exception) {
        Result.failure(CollectionDeserializeException(exception))
    }

    private val xmlTagDeserializer = XmlDanbooruTagDeserializer()
    private fun deserializeTag(element: Element): Result<XmlDanbooruTagDeserialize> =
        xmlTagDeserializer.deserializeTag(element.toString())
}

class JsonDanbooruTagsDeserializer : DanbooruTagsDeserializer {

    override fun deserializeTags(string: String): Result<JsonDanbooruTagsDeserialize> = try {
        Result.success(JsonDanbooruTagsDeserialize(JsonMapper().readValue<JsonNode>(string).map(::deserializeTag)))
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    private val jsonTagDeserializer = JsonDanbooruTagDeserializer()
    private fun deserializeTag(element: JsonNode): Result<JsonDanbooruTagDeserialize> =
        jsonTagDeserializer.deserializeTag(element.toString())
}