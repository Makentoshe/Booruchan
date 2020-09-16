package comment.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import comment.network.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface DanbooruCommentsDeserializer<in Response : DanbooruCommentsResponse.Success> {
    fun deserializeComments(response: Response): DanbooruCommentsDeserialize
}

class XmlDanbooruCommentsDeserializer :
    DanbooruCommentsDeserializer<XmlDanbooruCommentsResponse.Success> {

    override fun deserializeComments(response: XmlDanbooruCommentsResponse.Success): XmlDanbooruCommentsDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlDanbooruCommentsDeserialize(jsoup.getElementsByTag("comment").map(::deserializeComment))
    }

    private val xmlCommentDeserializer = XmlDanbooruCommentDeserializer()
    private fun deserializeComment(element: Element): XmlDanbooruCommentDeserialize =
        xmlCommentDeserializer.deserializeComment(XmlDanbooruCommentResponse.Success(element.toString()))
}

class JsonDanbooruCommentsDeserializer :
    DanbooruCommentsDeserializer<JsonDanbooruCommentsResponse.Success> {

    override fun deserializeComments(response: JsonDanbooruCommentsResponse.Success): JsonDanbooruCommentsDeserialize {
        return JsonDanbooruCommentsDeserialize(
            JsonMapper().readValue<JsonNode>(response.string).map(::deserializeComment)
        )
    }

    private val jsonCommentDeserializer = JsonDanbooruCommentDeserializer()
    private fun deserializeComment(element: JsonNode): JsonDanbooruCommentDeserialize =
        jsonCommentDeserializer.deserializeComment(JsonDanbooruCommentResponse.Success(element.toString()))
}
