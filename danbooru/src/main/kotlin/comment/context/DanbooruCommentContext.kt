package comment.context

import comment.deserialize.DanbooruCommentDeserialize
import comment.deserialize.JsonDanbooruCommentDeserializer
import comment.deserialize.XmlDanbooruCommentDeserializer
import comment.network.DanbooruCommentFilter
import comment.network.DanbooruCommentRequest
import comment.network.JsonDanbooruCommentRequest
import comment.network.XmlDanbooruCommentRequest

abstract class DanbooruCommentContext<Request : DanbooruCommentRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruCommentDeserialize<*>>
) : CommentContext<Request, DanbooruCommentFilter>(network, deserialize)

open class JsonDanbooruCommentContext(
    network: suspend (JsonDanbooruCommentRequest) -> Result<String>
) : DanbooruCommentContext<JsonDanbooruCommentRequest>(
    network, { json -> JsonDanbooruCommentDeserializer().deserializeComment(json) }
) {
    override fun buildRequest(filter: DanbooruCommentFilter) = JsonDanbooruCommentRequest(filter)
}

open class XmlDanbooruCommentContext(
    network: suspend (XmlDanbooruCommentRequest) -> Result<String>
) : DanbooruCommentContext<XmlDanbooruCommentRequest>(
    network, { xml -> XmlDanbooruCommentDeserializer().deserializeComment(xml) }
) {
    override fun buildRequest(filter: DanbooruCommentFilter) = XmlDanbooruCommentRequest(filter)
}
