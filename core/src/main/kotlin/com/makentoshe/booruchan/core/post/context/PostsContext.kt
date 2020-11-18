package com.makentoshe.booruchan.core.post.context

import com.makentoshe.booruchan.core.context.BooruEntityContext
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.network.PostsRequest

abstract class PostsContext<Request: PostsRequest, Filter: PostsFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<PostsDeserialize<*>>
) : BooruEntityContext<Request, PostsDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request

    abstract fun filterBuilder(): PostsFilter.Builder2
}