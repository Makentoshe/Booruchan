package com.makentoshe.booruchan.core.comment.context

import com.makentoshe.booruchan.core.comment.deserialize.CommentsDeserialize
import com.makentoshe.booruchan.core.comment.network.CommentsFilter
import com.makentoshe.booruchan.core.comment.network.CommentsRequest
import com.makentoshe.booruchan.core.context.BooruEntityContext

abstract class CommentsContext<Request: CommentsRequest, Filter: CommentsFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<CommentsDeserialize<*>>
) : BooruEntityContext<Request, CommentsDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}