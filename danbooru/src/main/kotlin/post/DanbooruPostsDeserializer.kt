package post

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import post.network.DanbooruPostsResponse
import post.network.JsonDanbooruPostResponse
import post.network.XmlDanbooruPostResponse

interface DanbooruPostsDeserializer<Posts : DanbooruPosts<*>> {
    fun deserializePosts(response: DanbooruPostsResponse.Success): Posts
}

class XmlDanbooruPostsDeserializer : DanbooruPostsDeserializer<XmlDanbooruPosts> {

    override fun deserializePosts(response: DanbooruPostsResponse.Success): XmlDanbooruPosts {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlDanbooruPosts(jsoup.getElementsByTag("post").mapNotNull(::deserializePost))
    }

    private val xmlPostDeserializer = XmlDanbooruPostDeserializer()
    private fun deserializePost(element: Element): XmlDanbooruPost? = try {
        xmlPostDeserializer.deserializePost(XmlDanbooruPostResponse.Success(element.toString()))
    } catch (vie: ValueInstantiationException) { // could not find one or more values in json
        null // skip
    }
}

class JsonDanbooruPostsDeserializer : DanbooruPostsDeserializer<JsonDanbooruPosts> {

    override fun deserializePosts(response: DanbooruPostsResponse.Success): JsonDanbooruPosts {
        return JsonDanbooruPosts(JsonMapper().readValue<JsonNode>(response.string).mapNotNull(::deserializePost))
    }

    private val jsonPostDeserializer = JsonDanbooruPostDeserializer()
    private fun deserializePost(element: JsonNode): JsonDanbooruPost? = try {
        jsonPostDeserializer.deserializePost(JsonDanbooruPostResponse.Success(element.toString()))
    } catch (vie: ValueInstantiationException) { // could not find one or more values in json
        null // skip
    }
}