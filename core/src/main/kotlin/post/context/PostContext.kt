package post.context

import context.BooruEntityContext
import post.deserialize.PostDeserialize
import post.network.PostRequest

open class PostContext<in Request: PostRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<PostDeserialize<*>>
): BooruEntityContext<Request, PostDeserialize<*>>(network, deserialize)
