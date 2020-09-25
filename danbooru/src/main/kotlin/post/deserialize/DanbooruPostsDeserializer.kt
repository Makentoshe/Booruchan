package post.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface DanbooruPostsDeserializer {
    fun deserializePosts(string: String): DanbooruPostsDeserialize
}

class XmlDanbooruPostsDeserializer : DanbooruPostsDeserializer {

    override fun deserializePosts(string: String): XmlDanbooruPostsDeserialize {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        return XmlDanbooruPostsDeserialize(jsoup.getElementsByTag("post").map(::deserializePost))
    }

    private val xmlPostDeserializer = XmlDanbooruPostDeserializer()
    private fun deserializePost(element: Element): XmlDanbooruPostDeserialize =
        xmlPostDeserializer.deserializePost(element.toString())
}

class JsonDanbooruPostsDeserializer : DanbooruPostsDeserializer {

    override fun deserializePosts(string: String): JsonDanbooruPostsDeserialize {
        return JsonDanbooruPostsDeserialize(JsonMapper().readValue<JsonNode>(string).map(::deserializePost))
    }

    private val jsonPostDeserializer = JsonDanbooruPostDeserializer()
    private fun deserializePost(element: JsonNode): JsonDanbooruPostDeserialize =
        jsonPostDeserializer.deserializePost(element.toString())
}