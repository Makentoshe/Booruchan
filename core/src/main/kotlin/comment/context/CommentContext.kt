package comment.context

import comment.deserialize.CommentDeserialize
import comment.network.CommentFilter
import comment.network.CommentRequest
import context.BooruEntityContext

abstract class CommentContext<Request: CommentRequest, Filter: CommentFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<CommentDeserialize<*>>
): BooruEntityContext<Request, CommentDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}
