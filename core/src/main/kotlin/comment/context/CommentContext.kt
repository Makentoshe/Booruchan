package comment.context

import comment.deserialize.CommentDeserialize
import comment.network.CommentRequest
import context.BooruEntityContext

open class CommentContext<in Request: CommentRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<CommentDeserialize<*>>
): BooruEntityContext<Request, CommentDeserialize<*>>(network, deserialize)
