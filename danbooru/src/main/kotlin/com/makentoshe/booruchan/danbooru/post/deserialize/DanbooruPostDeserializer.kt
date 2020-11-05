package com.makentoshe.booruchan.danbooru.post.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.makentoshe.booruchan.core.deserialize.EntityDeserializeException
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import com.makentoshe.booruchan.danbooru.post.JsonDanbooruPost
import com.makentoshe.booruchan.danbooru.post.XmlDanbooruPost

interface DanbooruPostDeserializer {
    fun deserializePost(string: String): Result<DanbooruPostDeserialize<*>>
}

class XmlDanbooruPostDeserializer : DanbooruPostDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializePost(string: String): Result<DanbooruPostDeserialize<XmlDanbooruPost>> {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        return try {
            val post = mapper.readValue<XmlDanbooruPost>(jsoup.children().toString().replace("\\s".toRegex(), ""))
            Result.success(DanbooruPostDeserialize(post, string))
        } catch (exception: Exception) {
            val map = mapper.readValue<Map<String, Any?>>(jsoup.children().toString().replace("\\s".toRegex(), ""))
            Result.failure(EntityDeserializeException(map, exception))
        }
    }
}

class JsonDanbooruPostDeserializer : DanbooruPostDeserializer {

    private val mapper = JsonMapper()

    override fun deserializePost(string: String): Result<DanbooruPostDeserialize<JsonDanbooruPost>> {
        return try {
            Result.success(DanbooruPostDeserialize(mapper.readValue(string), string))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(mapper.readValue(string), exception))
        }
    }
}