package comment.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import deserialize.DeserializeException
import deserialize.EntityDeserializeException
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import java.util.regex.Pattern

interface GelbooruCommentDeserializer {
    fun deserializeComment(string: String): Result<GelbooruCommentDeserialize<*>>
}

class XmlGelbooruCommentDeserializer : GelbooruCommentDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeComment(string: String): Result<XmlGelbooruCommentDeserialize> = try {
        val htmlPattern: Pattern = Pattern.compile(".*<[^>]+>.*", Pattern.DOTALL)
        if (!htmlPattern.matcher(string).matches()) throw Exception("Input string is not the valid xml")

        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        val xml = jsoup.getElementsByTag("comment").map { element ->
            element.attr("body", escapeValue(element.attr("body")))
        }.let(::Elements).toString()

        try {
            Result.success(GelbooruCommentDeserialize(mapper.readValue(xml)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(mapper.readValue(string), exception))
        }
    } catch (exception: Exception) {
        Result.failure(DeserializeException(exception, exception.message))
    }

    private fun escapeValue(value: String): String {
        return value.replace("<", "&lt").replace(">", "&gt")
    }
}
