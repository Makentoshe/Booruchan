package post.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import post.network.*

interface GelbooruPostsDeserializer<in Response : GelbooruPostsResponse.Success> {
    fun deserializePosts(response: Response): GelbooruPostsDeserialize
}

class XmlGelbooruPostsDeserializer : GelbooruPostsDeserializer<XmlGelbooruPostsResponse.Success> {

    override fun deserializePosts(response: XmlGelbooruPostsResponse.Success): XmlGelbooruPostsDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlGelbooruPostsDeserialize(jsoup.getElementsByTag("post").map(::deserializePost))
    }

    private val xmlPostDeserializer = XmlGelbooruPostDeserializer()
    private fun deserializePost(element: Element): XmlGelbooruPostDeserialize =
        xmlPostDeserializer.deserializePost(XmlGelbooruPostResponse.Success(element.toString()))
}

class JsonGelbooruPostsDeserializer : GelbooruPostsDeserializer<JsonGelbooruPostsResponse.Success> {

    override fun deserializePosts(response: JsonGelbooruPostsResponse.Success): JsonGelbooruPostsDeserialize {
        return JsonGelbooruPostsDeserialize(JsonMapper().readValue<JsonNode>(response.string).map(::deserializePost))
    }

    private val jsonPostDeserializer = JsonGelbooruPostDeserializer()
    private fun deserializePost(element: JsonNode): JsonGelbooruPostDeserialize =
        jsonPostDeserializer.deserializePost(JsonGelbooruPostResponse.Success(element.toString()))
}
