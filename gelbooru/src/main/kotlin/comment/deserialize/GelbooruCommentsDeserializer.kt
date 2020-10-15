package comment.deserialize

import deserialize.collectionDeserializeException
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import java.util.regex.Pattern

interface GelbooruCommentsDeserializer {
    fun deserializeComments(string: String): Result<GelbooruCommentsDeserialize<*>>
}

class XmlGelbooruCommentsDeserializer : GelbooruCommentsDeserializer {

    override fun deserializeComments(string: String): Result<XmlGelbooruCommentsDeserialize> = try {
        val htmlPattern: Pattern = Pattern.compile(".*<[^>]+>.*", Pattern.DOTALL)
        if (!htmlPattern.matcher(string).matches()) throw Exception("Input string is not the valid xml")

        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        Result.success(GelbooruCommentsDeserialize(jsoup.getElementsByTag("comment").map(::deserializeComment)))
    } catch (exception: Exception) {
        Result.failure(collectionDeserializeException(exception, "xml"))
    }

    private val xmlCommentDeserializer = XmlGelbooruCommentDeserializer()
    private fun deserializeComment(element: Element): Result<XmlGelbooruCommentDeserialize> =
        xmlCommentDeserializer.deserializeComment(element.toString())
}
