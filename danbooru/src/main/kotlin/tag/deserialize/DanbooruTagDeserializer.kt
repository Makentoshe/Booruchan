package tag.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.readValue
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import tag.network.DanbooruTagResponse
import tag.network.JsonDanbooruTagResponse
import tag.network.XmlDanbooruTagResponse

interface DanbooruTagDeserializer<in Response : DanbooruTagResponse.Success> {
    fun deserializeTag(response: Response): DanbooruTagDeserialize
}

class XmlDanbooruTagDeserializer : DanbooruTagDeserializer<XmlDanbooruTagResponse.Success> {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeTag(response: XmlDanbooruTagResponse.Success): XmlDanbooruTagDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        val xml = jsoup.children().toString().replace("\\s".toRegex(), "")
        return try {
            XmlDanbooruTagDeserialize.Success(mapper.readValue(xml))
        } catch (mkpe: MissingKotlinParameterException) {
            XmlDanbooruTagDeserialize.Failure(mapper.readValue(xml))
        } catch (mie: MismatchedInputException) {
            XmlDanbooruTagDeserialize.Failure(mapper.readValue(xml))
        }
    }
}

class JsonDanbooruTagDeserializer : DanbooruTagDeserializer<JsonDanbooruTagResponse.Success> {

    private val mapper = JsonMapper()

    override fun deserializeTag(response: JsonDanbooruTagResponse.Success): JsonDanbooruTagDeserialize {
        return try {
            JsonDanbooruTagDeserialize.Success(mapper.readValue(response.string))
        } catch (vie: ValueInstantiationException) {
            JsonDanbooruTagDeserialize.Failure(mapper.readValue(response.string))
        } catch (mie: MismatchedInputException) {
            JsonDanbooruTagDeserialize.Failure(mapper.readValue(response.string))
        }
    }
}