package comment.deserialize

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface DanbooruCommentsDeserializer {
    fun deserializeComments(string: String): DanbooruCommentsDeserialize
}

class XmlDanbooruCommentsDeserializer : DanbooruCommentsDeserializer {

    override fun deserializeComments(string: String): XmlDanbooruCommentsDeserialize {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        return XmlDanbooruCommentsDeserialize(jsoup.getElementsByTag("comment").map(::deserializeComment))
    }

    private val xmlCommentDeserializer = XmlDanbooruCommentDeserializer()
    private fun deserializeComment(element: Element): XmlDanbooruCommentDeserialize =
        xmlCommentDeserializer.deserializeComment(element.toString())
}

class JsonDanbooruCommentsDeserializer : DanbooruCommentsDeserializer {

    override fun deserializeComments(string: String): JsonDanbooruCommentsDeserialize {
        return JsonDanbooruCommentsDeserialize(
            JsonMapper().readValue<JsonNode>(string).map(::deserializeComment)
        )
    }

    private val jsonCommentDeserializer = JsonDanbooruCommentDeserializer()
    private fun deserializeComment(element: JsonNode): JsonDanbooruCommentDeserialize =
        jsonCommentDeserializer.deserializeComment(element.toString())
}
