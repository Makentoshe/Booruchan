package com.makentoshe.booruchan.gelbooru.post.deserialize

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
import com.makentoshe.booruchan.gelbooru.post.XmlGelbooruPost
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.jsoup.select.Elements

interface GelbooruPostDeserializer {
    fun deserializePost(string: String): Result<GelbooruPostDeserialize<*>>
}

class XmlGelbooruPostDeserializer : XmlGelbooruDeserializer(), GelbooruPostDeserializer {

    private val mapper = XmlMapper()

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializePost(string: String): Result<XmlGelbooruPostDeserialize> = try {
        if (!isValidXml(string)) throw Exception("Not valid xml")

        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        val xml = jsoup.getElementsByTag("post").map(::normalize).let(::Elements).toString()
        try {
            val post = mapper.readValue<XmlGelbooruPost>(xml)
            Result.success(GelbooruPostDeserialize(post, string))
        } catch (exception: Exception) {
            val map = mapper.readValue<Map<String, Any?>>(xml)
            Result.failure(EntityDeserializeException(string, map, exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(string, exception, string))
    }
}

class JsonGelbooruPostDeserializer : GelbooruPostDeserializer {

    private val mapper = JsonMapper()

    override fun deserializePost(string: String): Result<JsonGelbooruPostDeserialize> = try {
        val jsonNode = mapper.readValue<JsonNode>(string)
        val json = if (jsonNode.isArray) jsonNode.first().toString() else jsonNode.toString()
        Result.success(GelbooruPostDeserialize(mapper.readValue(json), string))
    } catch (exception: Exception) {
        Result.failure(EntityDeserializeException(string, mapper.readValue(string), exception))
    }
}
