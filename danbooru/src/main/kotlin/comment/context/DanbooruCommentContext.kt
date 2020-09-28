package comment.context

import comment.deserialize.DanbooruCommentDeserialize
import comment.deserialize.JsonDanbooruCommentDeserializer
import comment.deserialize.XmlDanbooruCommentDeserializer
import comment.network.DanbooruCommentRequest
import comment.network.JsonDanbooruCommentRequest
import comment.network.XmlDanbooruCommentRequest

open class DanbooruCommentContext<in Request: DanbooruCommentRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruCommentDeserialize<*>>
) : CommentContext<Request>(network, deserialize)

open class JsonDanbooruCommentContext(
    network: suspend (JsonDanbooruCommentRequest) -> Result<String>
) : DanbooruCommentContext<JsonDanbooruCommentRequest>(
    network, { json -> JsonDanbooruCommentDeserializer().deserializeComment(json) }
)

open class XmlDanbooruCommentContext(
    network: suspend (XmlDanbooruCommentRequest) -> Result<String>
) : DanbooruCommentContext<XmlDanbooruCommentRequest>(
    network, { xml -> XmlDanbooruCommentDeserializer().deserializeComment(xml) }
)
