package comment.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.readValue
import comment.network.DanbooruCommentResponse
import comment.network.JsonDanbooruCommentResponse
import comment.network.XmlDanbooruCommentResponse
import org.codehaus.stax2.XMLInputFactory2
import org.codehaus.stax2.XMLOutputFactory2
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

interface DanbooruCommentDeserializer<in Response : DanbooruCommentResponse.Success> {
    fun deserializeComment(response: Response): DanbooruCommentDeserialize
}

class XmlDanbooruCommentDeserializer : DanbooruCommentDeserializer<XmlDanbooruCommentResponse.Success> {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializeComment(response: XmlDanbooruCommentResponse.Success): XmlDanbooruCommentDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        val xml = jsoup.children().toString().replace("\\s".toRegex(), "")
        return try {
            XmlDanbooruCommentDeserialize.Success(mapper.readValue(xml))
        } catch (mkpe: MissingKotlinParameterException) {
            XmlDanbooruCommentDeserialize.Failure(mapper.readValue(xml))
        } catch (mie: MismatchedInputException) {
            XmlDanbooruCommentDeserialize.Failure(mapper.readValue(xml))
        }
    }
}

class JsonDanbooruCommentDeserializer : DanbooruCommentDeserializer<JsonDanbooruCommentResponse.Success> {

    private val mapper = JsonMapper()

    override fun deserializeComment(response: JsonDanbooruCommentResponse.Success): JsonDanbooruCommentDeserialize {
        return try {
            JsonDanbooruCommentDeserialize.Success(mapper.readValue(response.string))
        } catch (vie: ValueInstantiationException) {
            JsonDanbooruCommentDeserialize.Failure(mapper.readValue(response.string))
        } catch (mie: MismatchedInputException) {
            JsonDanbooruCommentDeserialize.Failure(mapper.readValue(response.string))
        }
    }
}
