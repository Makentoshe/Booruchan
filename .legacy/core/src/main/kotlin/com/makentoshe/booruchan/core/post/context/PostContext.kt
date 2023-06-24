package com.makentoshe.booruchan.core.post.context

import com.makentoshe.booruchan.core.context.BooruEntityContext
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import com.makentoshe.booruchan.core.post.network.PostFilter
import com.makentoshe.booruchan.core.post.network.PostRequest

abstract class PostContext<Request: PostRequest, Filter: PostFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<PostDeserialize<*>>
): BooruEntityContext<Request, PostDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}
