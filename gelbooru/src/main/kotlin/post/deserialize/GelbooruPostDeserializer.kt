package post.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import post.network.GelbooruPostResponse
import post.network.JsonGelbooruPostResponse
import post.network.XmlGelbooruPostResponse

interface GelbooruPostDeserializer<in Response : GelbooruPostResponse.Success> {
    fun deserializePost(response: Response): GelbooruPostDeserialize
}

class XmlGelbooruPostDeserializer : GelbooruPostDeserializer<XmlGelbooruPostResponse.Success> {

    private val mapper = XmlMapper()

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializePost(response: XmlGelbooruPostResponse.Success): XmlGelbooruPostDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        val xml = jsoup.children().toString()
        return try {
            XmlGelbooruPostDeserialize.Success(mapper.readValue(xml))
        } catch (exception: Exception) {
            XmlGelbooruPostDeserialize.Failure(mapper.readValue(xml), exception)
        }
    }
}

class JsonGelbooruPostDeserializer : GelbooruPostDeserializer<JsonGelbooruPostResponse.Success> {

    private val mapper = JsonMapper()

    override fun deserializePost(response: JsonGelbooruPostResponse.Success): JsonGelbooruPostDeserialize {
        return try {
            JsonGelbooruPostDeserialize.Success(mapper.readValue(response.string))
        } catch (exception: Exception) {
            JsonGelbooruPostDeserialize.Failure(mapper.readValue(response.string), exception)
        }
    }
}
