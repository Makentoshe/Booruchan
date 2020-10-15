package comment.context

import comment.deserialize.GelbooruCommentsDeserialize
import comment.deserialize.XmlGelbooruCommentsDeserializer
import comment.network.GelbooruCommentsFilter
import comment.network.GelbooruCommentsRequest
import comment.network.XmlGelbooruCommentsRequest

abstract class GelbooruCommentsContext<Request : GelbooruCommentsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<GelbooruCommentsDeserialize<*>>
) : CommentsContext<Request, GelbooruCommentsFilter>(network, deserialize)

open class XmlGelbooruCommentsContext(
    network: suspend (GelbooruCommentsRequest) -> Result<String>
) : GelbooruCommentsContext<XmlGelbooruCommentsRequest>(
    network, { json -> XmlGelbooruCommentsDeserializer().deserializeComments(json) }
) {
    override fun buildRequest(filter: GelbooruCommentsFilter) = XmlGelbooruCommentsRequest(filter)
}
