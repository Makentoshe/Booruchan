package post

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
import post.network.DanbooruPostResponse

interface DanbooruPostDeserializer<out Post : DanbooruPost> {
    fun deserializePost(response: DanbooruPostResponse.Success): Post
}

class XmlDanbooruPostDeserializer : DanbooruPostDeserializer<XmlDanbooruPost> {

    private val mapper = XmlMapper(XMLInputFactory2.newFactory(), XMLOutputFactory2.newFactory())

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializePost(response: DanbooruPostResponse.Success): XmlDanbooruPost {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        jsoup.allElements.forEach { element -> element.clearAttributes() }
        return mapper.readValue(jsoup.children().toString().replace("\\s".toRegex(), ""))
    }
}

class JsonDanbooruPostDeserializer : DanbooruPostDeserializer<JsonDanbooruPost> {

    private val mapper = JsonMapper()

    override fun deserializePost(response: DanbooruPostResponse.Success): JsonDanbooruPost {
        return mapper.readValue(response.string)
    }
}