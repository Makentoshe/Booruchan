package com.makentoshe.booruchan.gelbooru.comment.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
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

interface GelbooruCommentDeserializer {
    fun deserializeComment(string: String): Result<GelbooruCommentDeserialize<*>>
}

class XmlGelbooruCommentDeserializer : XmlGelbooruDeserializer(), GelbooruCommentDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeComment(string: String): Result<XmlGelbooruCommentDeserialize> = try {
        if (!isValidXml(string)) throw Exception("Not valid xml")

        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        val xml = jsoup.getElementsByTag("comment").map(::normalize).let(::Elements).toString()
        try {
            Result.success(GelbooruCommentDeserialize(mapper.readValue(xml)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(mapper.readValue(string), exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(exception, string))
    }
}
