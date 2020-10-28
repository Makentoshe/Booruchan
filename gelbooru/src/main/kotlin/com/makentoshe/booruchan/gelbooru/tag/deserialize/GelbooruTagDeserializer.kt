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
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.jsoup.select.Elements

interface GelbooruTagDeserializer {
    fun deserializeTag(string: String): Result<GelbooruTagDeserialize<*>>
}

class XmlGelbooruTagDeserializer : XmlGelbooruDeserializer(), GelbooruTagDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(string: String): Result<XmlGelbooruTagDeserialize> = try {
        if (!isValidXml(string)) throw Exception("Not valid xml")

        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        val xml = jsoup.getElementsByTag("tag").map(::normalize).let(::Elements).toString()
        try {
            Result.success(XmlGelbooruTagDeserialize(mapper.readValue(xml)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(mapper.readValue(xml), exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(exception, exception.localizedMessage))
    }
}

class JsonGelbooruTagDeserializer : GelbooruTagDeserializer {

    private val mapper = JsonMapper()

    override fun deserializeTag(string: String): Result<JsonGelbooruTagDeserialize> = try {
        val jsonNode = mapper.readValue<JsonNode>(string)
        val json = if (jsonNode.isArray) jsonNode.first().toString() else jsonNode.toString()
        try {
            Result.success(JsonGelbooruTagDeserialize(mapper.readValue(json)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(mapper.readValue(string), exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(exception, exception.localizedMessage))
    }
}