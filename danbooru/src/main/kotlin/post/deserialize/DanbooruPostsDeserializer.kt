package post.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import deserialize.CollectionDeserializeException
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import post.JsonDanbooruPost
import post.XmlDanbooruPost

interface DanbooruPostsDeserializer {
    fun deserializePosts(string: String): Result<DanbooruPostsDeserialize<*>>
}

class XmlDanbooruPostsDeserializer : DanbooruPostsDeserializer {

    override fun deserializePosts(string: String): Result<DanbooruPostsDeserialize<XmlDanbooruPost>> = try {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        val results = jsoup.getElementsByTag("post").map(::deserializePost)
        Result.success(DanbooruPostsDeserialize(results))
    } catch (exception: Exception) {
        Result.failure(CollectionDeserializeException(exception))
    }

    private val xmlPostDeserializer = XmlDanbooruPostDeserializer()
    private fun deserializePost(element: Element): Result<DanbooruPostDeserialize<XmlDanbooruPost>> =
        xmlPostDeserializer.deserializePost(element.toString())
}

class JsonDanbooruPostsDeserializer : DanbooruPostsDeserializer {

    override fun deserializePosts(string: String): Result<DanbooruPostsDeserialize<JsonDanbooruPost>> = try {
        val results = JsonMapper().readValue<JsonNode>(string).map(::deserializePost)
        Result.success(DanbooruPostsDeserialize(results))
    } catch (exception: Exception) {
        Result.failure(CollectionDeserializeException(exception))
    }

    private val jsonPostDeserializer = JsonDanbooruPostDeserializer()
    private fun deserializePost(element: JsonNode): Result<DanbooruPostDeserialize<JsonDanbooruPost>> =
        jsonPostDeserializer.deserializePost(element.toString())
}