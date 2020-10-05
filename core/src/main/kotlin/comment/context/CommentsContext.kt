package comment.context

import comment.deserialize.CommentsDeserialize
import comment.network.CommentsFilter
import comment.network.CommentsRequest
import context.BooruEntityContext

abstract class CommentsContext<Request: CommentsRequest, Filter: CommentsFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<CommentsDeserialize<*>>
) : BooruEntityContext<Request, CommentsDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}