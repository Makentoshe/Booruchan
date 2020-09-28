package post.context

import context.BooruEntityContext
import post.deserialize.PostsDeserialize
import post.network.PostsRequest

open class PostsContext<in Request: PostsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<PostsDeserialize<*>>
) : BooruEntityContext<Request, PostsDeserialize<*>>(network, deserialize)