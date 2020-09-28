package tag.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import deserialize.EntityDeserializeException
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

interface DanbooruTagDeserializer {
    fun deserializeTag(string: String): Result<DanbooruTagDeserialize<*>>
}

class XmlDanbooruTagDeserializer : DanbooruTagDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(string: String): Result<XmlDanbooruTagDeserialize> = try {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        val xml = jsoup.children().toString().replace("\\s".toRegex(), "")
        try {
            Result.success(XmlDanbooruTagDeserialize(mapper.readValue(xml)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(mapper.readValue(xml), exception))
        }
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}

class JsonDanbooruTagDeserializer : DanbooruTagDeserializer {

    private val mapper = JsonMapper()

    override fun deserializeTag(string: String): Result<JsonDanbooruTagDeserialize> = try {
        try {
            Result.success(JsonDanbooruTagDeserialize(mapper.readValue(string)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(mapper.readValue(string), exception))
        }
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}