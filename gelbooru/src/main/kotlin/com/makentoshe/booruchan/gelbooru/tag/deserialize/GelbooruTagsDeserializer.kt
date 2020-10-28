package com.makentoshe.booruchan.gelbooru.tag.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.makentoshe.booruchan.core.deserialize.collectionDeserializeException
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import java.util.regex.Pattern

interface GelbooruTagsDeserializer {
    fun deserializeTags(string: String): Result<GelbooruTagsDeserialize<*>>
}

class XmlGelbooruTagsDeserializer : GelbooruTagsDeserializer {

    override fun deserializeTags(string: String): Result<XmlGelbooruTagsDeserialize> = try {
        val htmlPattern: Pattern = Pattern.compile(".*<[^>]+>.*", Pattern.DOTALL)
        if (!htmlPattern.matcher(string).matches()) throw Exception("Input string is not the valid xml")

        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        Result.success(XmlGelbooruTagsDeserialize(jsoup.getElementsByTag("tag").map(::deserializeTag)))
    } catch (exception: Exception) {
        Result.failure(collectionDeserializeException(exception, "xml"))
    }

    private val xmlTagDeserializer = XmlGelbooruTagDeserializer()
    private fun deserializeTag(element: Element): Result<XmlGelbooruTagDeserialize> =
        xmlTagDeserializer.deserializeTag(element.toString())
}

class JsonGelbooruTagsDeserializer : GelbooruTagsDeserializer {

    override fun deserializeTags(string: String): Result<JsonGelbooruTagsDeserialize> = try {
        Result.success(JsonGelbooruTagsDeserialize(JsonMapper().readValue<JsonNode>(string).map(::deserializeTag)))
    } catch (exception: Exception) {
        Result.failure(collectionDeserializeException(exception, "json"))
    }

    private val jsonTagDeserializer = JsonGelbooruTagDeserializer()
    private fun deserializeTag(element: JsonNode): Result<JsonGelbooruTagDeserialize> =
        jsonTagDeserializer.deserializeTag(element.toString())
}
