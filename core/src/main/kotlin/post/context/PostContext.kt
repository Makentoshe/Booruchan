package post.context

import context.BooruEntityContext
import post.deserialize.PostDeserialize
import post.network.PostFilter
import post.network.PostRequest

abstract class PostContext<Request: PostRequest, Filter: PostFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<PostDeserialize<*>>
): BooruEntityContext<Request, PostDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}
