package comment.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import comment.network.GelbooruCommentResponse
import comment.network.XmlGelbooruCommentResponse
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.jsoup.select.Elements

interface GelbooruCommentDeserializer<in Response : GelbooruCommentResponse.Success> {
    fun deserializeComment(response: Response): GelbooruCommentDeserialize
}

class XmlGelbooruCommentDeserializer : GelbooruCommentDeserializer<XmlGelbooruCommentResponse.Success> {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeComment(response: XmlGelbooruCommentResponse.Success): XmlGelbooruCommentDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        val xml = jsoup.getElementsByTag("comment").map { element ->
            element.attr("body", escapeValue(element.attr("body")))
        }.let(::Elements).toString()
        return try {
            XmlGelbooruCommentDeserialize.Success(mapper.readValue(xml))
        } catch (e: Exception) {
            XmlGelbooruCommentDeserialize.Failure(e, mapper.readValue(xml))
        }
    }

    private fun escapeValue(value: String): String {
        return value.replace("<", "&lt").replace(">", "&gt")
    }
}
