package tag.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import tag.network.GelbooruTagResponse
import tag.network.JsonGelbooruTagResponse
import tag.network.XmlGelbooruTagResponse

interface GelbooruTagDeserializer<in Response : GelbooruTagResponse.Success> {
    fun deserializeTag(response: Response): GelbooruTagDeserialize
}

class XmlGelbooruTagDeserializer : GelbooruTagDeserializer<XmlGelbooruTagResponse.Success> {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(response: XmlGelbooruTagResponse.Success): XmlGelbooruTagDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        val xml = jsoup.getElementsByTag("tag").toString()
        return try {
            XmlGelbooruTagDeserialize.Success(mapper.readValue(xml))
        } catch (e: Exception) {
            XmlGelbooruTagDeserialize.Failure(e, mapper.readValue(xml))
        }
    }
}

class JsonGelbooruTagDeserializer : GelbooruTagDeserializer<JsonGelbooruTagResponse.Success> {

    private val mapper = JsonMapper()

    override fun deserializeTag(response: JsonGelbooruTagResponse.Success): JsonGelbooruTagDeserialize {
        val node = JsonMapper().readValue<JsonNode>(response.string)
        val json = if (node.isObject) node.toString() else node.first().toString()
        return try {
            JsonGelbooruTagDeserialize.Success(mapper.readValue(json))
        } catch (e: Exception) {
            JsonGelbooruTagDeserialize.Failure(e, mapper.readValue(json))
        }
    }
}