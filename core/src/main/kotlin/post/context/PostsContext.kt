package post.context

import context.BooruEntityContext
import post.deserialize.PostsDeserialize
import post.network.PostsFilter
import post.network.PostsRequest

abstract class PostsContext<Request: PostsRequest, Filter: PostsFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<PostsDeserialize<*>>
) : BooruEntityContext<Request, PostsDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}