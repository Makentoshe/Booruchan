package com.makentoshe.booruchan.danbooru.comment.deserialize

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

interface DanbooruCommentDeserializer {
    fun deserializeComment(string: String): Result<DanbooruCommentDeserialize<*>>
}

class XmlDanbooruCommentDeserializer : DanbooruCommentDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeComment(string: String): Result<XmlDanbooruCommentDeserialize> {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        val xml = jsoup.children().toString().replace("\\s".toRegex(), "")
        return try {
            Result.success(DanbooruCommentDeserialize(mapper.readValue(xml)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(string, mapper.readValue(string), exception))
        }
    }
}

class JsonDanbooruCommentDeserializer : DanbooruCommentDeserializer {

    private val mapper = JsonMapper()

    override fun deserializeComment(string: String): Result<JsonDanbooruCommentDeserialize> {
        return try {
            Result.success(DanbooruCommentDeserialize(mapper.readValue(string)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(string, mapper.readValue(string), exception))
        }
    }
}
