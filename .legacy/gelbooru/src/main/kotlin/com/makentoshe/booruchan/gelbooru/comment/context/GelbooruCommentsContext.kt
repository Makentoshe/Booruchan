package com.makentoshe.booruchan.gelbooru.comment.context

import com.makentoshe.booruchan.core.comment.context.CommentsContext
import com.makentoshe.booruchan.gelbooru.comment.deserialize.GelbooruCommentsDeserialize
import com.makentoshe.booruchan.gelbooru.comment.deserialize.XmlGelbooruCommentsDeserializer
import com.makentoshe.booruchan.gelbooru.comment.network.GelbooruCommentsFilter
import com.makentoshe.booruchan.gelbooru.comment.network.GelbooruCommentsRequest
import com.makentoshe.booruchan.gelbooru.comment.network.XmlGelbooruCommentsRequest

abstract class GelbooruCommentsContext<Request : GelbooruCommentsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<GelbooruCommentsDeserialize<*>>
) : CommentsContext<Request, GelbooruCommentsFilter>(network, deserialize)

open class XmlGelbooruCommentsContext(
    network: suspend (GelbooruCommentsRequest) -> Result<String>
) : GelbooruCommentsContext<XmlGelbooruCommentsRequest>(
    network, { json -> XmlGelbooruCommentsDeserializer().deserializeComments(json) }
) {
    override fun buildRequest(filter: GelbooruCommentsFilter) = XmlGelbooruCommentsRequest(filter)
}
