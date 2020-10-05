package comment.context

import comment.deserialize.DanbooruCommentsDeserialize
import comment.deserialize.JsonDanbooruCommentsDeserializer
import comment.deserialize.XmlDanbooruCommentsDeserializer
import comment.network.DanbooruCommentsFilter
import comment.network.DanbooruCommentsRequest
import comment.network.JsonDanbooruCommentsRequest
import comment.network.XmlDanbooruCommentsRequest

abstract class DanbooruCommentsContext<Request : DanbooruCommentsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruCommentsDeserialize<*>>
) : CommentsContext<Request, DanbooruCommentsFilter>(network, deserialize)

open class JsonDanbooruCommentsContext(
    network: suspend (JsonDanbooruCommentsRequest) -> Result<String>
) : DanbooruCommentsContext<JsonDanbooruCommentsRequest>(
    network, { json -> JsonDanbooruCommentsDeserializer().deserializeComments(json) }
) {
    override fun buildRequest(filter: DanbooruCommentsFilter) = JsonDanbooruCommentsRequest(filter)
}

open class XmlDanbooruCommentsContext(
    network: suspend (DanbooruCommentsRequest) -> Result<String>
) : DanbooruCommentsContext<XmlDanbooruCommentsRequest>(
    network, { json -> XmlDanbooruCommentsDeserializer().deserializeComments(json) }
) {
    override fun buildRequest(filter: DanbooruCommentsFilter) = XmlDanbooruCommentsRequest(filter)
}
