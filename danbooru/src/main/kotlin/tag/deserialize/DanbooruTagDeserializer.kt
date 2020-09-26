package tag.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

interface DanbooruTagDeserializer {
    fun deserializeTag(string: String): DanbooruTagDeserialize
}

class XmlDanbooruTagDeserializer : DanbooruTagDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(string: String): XmlDanbooruTagDeserialize {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        val xml = jsoup.children().toString().replace("\\s".toRegex(), "")
        return try {
            XmlDanbooruTagDeserialize.Success(mapper.readValue(xml))
        } catch (e: Exception) {
            XmlDanbooruTagDeserialize.Failure(mapper.readValue(xml))
        }
    }
}

class JsonDanbooruTagDeserializer : DanbooruTagDeserializer {

    private val mapper = JsonMapper()

    override fun deserializeTag(string: String): JsonDanbooruTagDeserialize {
        return try {
            JsonDanbooruTagDeserialize.Success(mapper.readValue(string))
        } catch (e: Exception) {
            JsonDanbooruTagDeserialize.Failure(mapper.readValue(string))
        }
    }
}