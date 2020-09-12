package post.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import post.*
import post.network.DanbooruPostsResponse
import post.network.JsonDanbooruPostResponse
import post.network.XmlDanbooruPostResponse

interface DanbooruPostsDeserializer<Posts : DanbooruPostsDeserialize<*>> {
    fun deserializePosts(response: DanbooruPostsResponse.Success): Posts
}

class XmlDanbooruPostsDeserializer : DanbooruPostsDeserializer<XmlDanbooruPostsDeserialize> {

    override fun deserializePosts(response: DanbooruPostsResponse.Success): XmlDanbooruPostsDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlDanbooruPostsDeserialize(jsoup.getElementsByTag("post").map(::deserializePost))
    }

    private val xmlPostDeserializer = XmlDanbooruPostDeserializer()
    private fun deserializePost(element: Element): XmlDanbooruPostDeserialize = try {
        xmlPostDeserializer.deserializePost(XmlDanbooruPostResponse.Success(element.toString()))
    } catch (vie: ValueInstantiationException) {
        XmlDanbooruPostDeserialize.Failure(emptyMap()) //todo
    }
}

class JsonDanbooruPostsDeserializer : DanbooruPostsDeserializer<JsonDanbooruPostsDeserialize> {

    override fun deserializePosts(response: DanbooruPostsResponse.Success): JsonDanbooruPostsDeserialize {
        return JsonDanbooruPostsDeserialize(JsonMapper().readValue<JsonNode>(response.string).map(::deserializePost))
    }

    private val jsonPostDeserializer = JsonDanbooruPostDeserializer()
    private fun deserializePost(element: JsonNode): JsonDanbooruPostDeserialize = try {
        jsonPostDeserializer.deserializePost(JsonDanbooruPostResponse.Success(element.toString()))
    } catch (vie: ValueInstantiationException) {
        JsonDanbooruPostDeserialize.Failure(emptyMap()) //todo
    }
}