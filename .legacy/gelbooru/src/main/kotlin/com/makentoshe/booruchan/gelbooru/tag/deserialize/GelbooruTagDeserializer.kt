package com.makentoshe.booruchan.gelbooru.tag.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.makentoshe.booruchan.core.deserialize.DeserializeException
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import com.makentoshe.booruchan.gelbooru.deserialize.XmlGelbooruDeserializer
import com.makentoshe.booruchan.gelbooru.tag.XmlGelbooruTag
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.jsoup.select.Elements

interface GelbooruTagDeserializer {
    fun deserializeTag(string: String): Result<GelbooruTagDeserialize<*>>
}

class XmlGelbooruTagDeserializer : XmlGelbooruDeserializer(), GelbooruTagDeserializer {

    private val mapper = XmlMapper()

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(string: String): Result<XmlGelbooruTagDeserialize> = try {
        if (!isValidXml(string)) throw Exception("Not valid xml")

        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        val xml = jsoup.getElementsByTag("tag").map(::normalize).let(::Elements).toString()
        try {
            val tag = mapper.readValue<XmlGelbooruTag>(xml)
            Result.success(GelbooruTagDeserialize(tag, xml))
        } catch (exception: Exception) {
            val map = mapper.readValue<Map<String, Any?>>(xml)
            Result.failure(EntityDeserializeException(string, map, exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(string, exception, exception.localizedMessage))
    }
}

class JsonGelbooruTagDeserializer : GelbooruTagDeserializer {

    private val mapper = JsonMapper()

    override fun deserializeTag(string: String): Result<JsonGelbooruTagDeserialize> = try {
        val jsonNode = mapper.readValue<JsonNode>(string)
        val json = if (jsonNode.isArray) jsonNode.first().toString() else jsonNode.toString()

        try {
            Result.success(JsonGelbooruTagDeserialize(mapper.readValue(json), json))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(json, mapper.readValue(string), exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(string, exception, exception.localizedMessage))
    }
}