package comment.deserialize

import comment.network.GelbooruCommentsResponse
import comment.network.XmlGelbooruCommentResponse
import comment.network.XmlGelbooruCommentsResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

interface GelbooruCommentsDeserializer<in Response : GelbooruCommentsResponse.Success> {
    fun deserializeComments(response: Response): GelbooruCommentsDeserialize
}

class XmlGelbooruCommentsDeserializer :
    GelbooruCommentsDeserializer<XmlGelbooruCommentsResponse.Success> {

    override fun deserializeComments(response: XmlGelbooruCommentsResponse.Success): XmlGelbooruCommentsDeserialize {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        return XmlGelbooruCommentsDeserialize(jsoup.getElementsByTag("comment").map(::deserializeComment))
    }

    private val xmlCommentDeserializer = XmlGelbooruCommentDeserializer()
    private fun deserializeComment(element: Element): XmlGelbooruCommentDeserialize =
        xmlCommentDeserializer.deserializeComment(XmlGelbooruCommentResponse.Success(element.toString()))
}
