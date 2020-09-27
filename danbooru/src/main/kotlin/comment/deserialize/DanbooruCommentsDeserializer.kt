package comment.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import comment.JsonDanbooruComment
import comment.XmlDanbooruComment
import deserialize.CollectionDeserializeException
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface DanbooruCommentsDeserializer {
    fun deserializeComments(string: String): Result<DanbooruCommentsDeserialize<*>>
}

class XmlDanbooruCommentsDeserializer : DanbooruCommentsDeserializer {

    override fun deserializeComments(string: String): Result<DanbooruCommentsDeserialize<XmlDanbooruComment>> = try {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        Result.success(DanbooruCommentsDeserialize(jsoup.getElementsByTag("comment").map(::deserializeComment)))
    } catch (exception: Exception) {
        Result.failure(CollectionDeserializeException(exception))
    }

    private val xmlCommentDeserializer = XmlDanbooruCommentDeserializer()
    private fun deserializeComment(element: Element): Result<DanbooruCommentDeserialize<XmlDanbooruComment>> =
        xmlCommentDeserializer.deserializeComment(element.toString())
}

class JsonDanbooruCommentsDeserializer : DanbooruCommentsDeserializer {

    override fun deserializeComments(string: String): Result<DanbooruCommentsDeserialize<JsonDanbooruComment>> = try {
        Result.success(DanbooruCommentsDeserialize(JsonMapper().readValue<JsonNode>(string).map(::deserializeComment)))
    } catch (exception: Exception) {
        Result.failure(CollectionDeserializeException(exception))
    }

    private val jsonCommentDeserializer = JsonDanbooruCommentDeserializer()
    private fun deserializeComment(element: JsonNode): Result<DanbooruCommentDeserialize<JsonDanbooruComment>> =
        jsonCommentDeserializer.deserializeComment(element.toString())
}
