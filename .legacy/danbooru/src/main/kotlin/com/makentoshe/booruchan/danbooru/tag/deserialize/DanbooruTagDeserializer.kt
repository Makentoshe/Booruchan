package com.makentoshe.booruchan.danbooru.tag.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.makentoshe.booruchan.core.deserialize.DeserializeException
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.danbooru.tag.XmlDanbooruTag
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

interface DanbooruTagDeserializer {
    fun deserializeTag(string: String): Result<DanbooruTagDeserialize<*>>
}

class XmlDanbooruTagDeserializer : DanbooruTagDeserializer {

    private val mapper = XmlMapper()

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(string: String): Result<XmlDanbooruTagDeserialize> = try {
        // TODO check xml is valid

        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        val xml = jsoup.children().toString().replace("\\s".toRegex(), "")

        try {
            val tag = mapper.readValue<XmlDanbooruTag>(xml)
            Result.success(XmlDanbooruTagDeserialize(tag, xml))
        } catch (exception: Exception) {
            val map = mapper.readValue<Map<String, Any?>>(xml)
            Result.failure(EntityDeserializeException(string, map, exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(string, exception, exception.localizedMessage))
    }
}

class JsonDanbooruTagDeserializer : DanbooruTagDeserializer {

    private val mapper = JsonMapper()

    override fun deserializeTag(string: String): Result<JsonDanbooruTagDeserialize> = try {
        val jsonNode = mapper.readValue<JsonNode>(string)
        val json = if (jsonNode.isArray) jsonNode.first().toString() else jsonNode.toString()

        try {
            Result.success(JsonDanbooruTagDeserialize(mapper.readValue(json), json))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(json, mapper.readValue(json), exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(string, exception, exception.localizedMessage))
    }
}