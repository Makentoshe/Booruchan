package comment.context

import comment.deserialize.CommentsDeserialize
import comment.network.CommentsRequest
import context.BooruEntityContext

open class CommentsContext<Request: CommentsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<CommentsDeserialize<*>>
) : BooruEntityContext<Request, CommentsDeserialize<*>>(network, deserialize)