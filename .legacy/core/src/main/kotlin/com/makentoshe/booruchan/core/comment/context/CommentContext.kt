package com.makentoshe.booruchan.core.comment.context

import com.makentoshe.booruchan.core.comment.deserialize.CommentDeserialize
import com.makentoshe.booruchan.core.comment.network.CommentFilter
import com.makentoshe.booruchan.core.comment.network.CommentRequest
import com.makentoshe.booruchan.core.context.BooruEntityContext

abstract class CommentContext<Request: CommentRequest, Filter: CommentFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<CommentDeserialize<*>>
): BooruEntityContext<Request, CommentDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}
