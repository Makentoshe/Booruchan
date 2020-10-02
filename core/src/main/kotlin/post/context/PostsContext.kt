package post.context

import context.BooruEntityContext
import post.deserialize.PostsDeserialize
import post.network.PostsRequest

abstract class PostsContext<Request: PostsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<PostsDeserialize<*>>
) : BooruEntityContext<Request, PostsDeserialize<*>>(network, deserialize)