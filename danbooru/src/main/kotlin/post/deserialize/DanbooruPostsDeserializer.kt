package post.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import post.network.*

interface DanbooruPostsDeserializer<in Response : DanbooruPostsResponse.Success> {
    fun deserializePosts(response: Response): DanbooruPostsDeserialize
}

class XmlDanbooruPostsDeserializer :
    DanbooruPostsDeserializer<XmlDanbooruPostsResponse.Success> {

    override fun deserializePosts(response: XmlDanbooruPostsResponse.Success): XmlDanbooruPostsDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlDanbooruPostsDeserialize(jsoup.getElementsByTag("post").map(::deserializePost))
    }

    private val xmlPostDeserializer = XmlDanbooruPostDeserializer()
    private fun deserializePost(element: Element): XmlDanbooruPostDeserialize =
        xmlPostDeserializer.deserializePost(XmlDanbooruPostResponse.Success(element.toString()))
}

class JsonDanbooruPostsDeserializer :
    DanbooruPostsDeserializer<JsonDanbooruPostsResponse.Success> {

    override fun deserializePosts(response: JsonDanbooruPostsResponse.Success): JsonDanbooruPostsDeserialize {
        return JsonDanbooruPostsDeserialize(JsonMapper().readValue<JsonNode>(response.string).map(::deserializePost))
    }

    private val jsonPostDeserializer = JsonDanbooruPostDeserializer()
    private fun deserializePost(element: JsonNode): JsonDanbooruPostDeserialize =
        jsonPostDeserializer.deserializePost(JsonDanbooruPostResponse.Success(element.toString()))
}