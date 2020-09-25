package post.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import post.XmlDanbooruPost

interface DanbooruPostDeserializer {
    fun deserializePost(string: String): DanbooruPostDeserialize
}

class XmlDanbooruPostDeserializer : DanbooruPostDeserializer {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializePost(string: String): XmlDanbooruPostDeserialize {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        return try {
            val post = mapper.readValue<XmlDanbooruPost>(jsoup.children().toString().replace("\\s".toRegex(), ""))
            XmlDanbooruPostDeserialize.Success(post)
        } catch (e: Exception) {
            val map = mapper.readValue<Map<String, Any?>>(jsoup.children().toString().replace("\\s".toRegex(), ""))
            XmlDanbooruPostDeserialize.Failure(map)
        }
    }
}

class JsonDanbooruPostDeserializer : DanbooruPostDeserializer {

    private val mapper = JsonMapper()

    override fun deserializePost(string: String): JsonDanbooruPostDeserialize {
        return try {
            JsonDanbooruPostDeserialize.Success(mapper.readValue(string))
        } catch (vie: ValueInstantiationException) {
            JsonDanbooruPostDeserialize.Failure(mapper.readValue(string))
        }
    }
}