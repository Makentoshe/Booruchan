package com.makentoshe.booruchan.danbooru.comment.context

import com.makentoshe.booruchan.core.comment.context.CommentsContext
import com.makentoshe.booruchan.danbooru.comment.deserialize.DanbooruCommentsDeserialize
import com.makentoshe.booruchan.danbooru.comment.deserialize.JsonDanbooruCommentsDeserializer
import com.makentoshe.booruchan.danbooru.comment.deserialize.XmlDanbooruCommentsDeserializer
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentsFilter
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentsRequest
import com.makentoshe.booruchan.danbooru.comment.network.JsonDanbooruCommentsRequest
import com.makentoshe.booruchan.danbooru.comment.network.XmlDanbooruCommentsRequest

abstract class DanbooruCommentsContext<Request : DanbooruCommentsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruCommentsDeserialize<*>>
) : CommentsContext<Request, DanbooruCommentsFilter>(network, deserialize)

open class JsonDanbooruCommentsContext(
    network: suspend (JsonDanbooruCommentsRequest) -> Result<String>
) : DanbooruCommentsContext<JsonDanbooruCommentsRequest>(
    network, { json -> JsonDanbooruCommentsDeserializer().deserializeComments(json) }
) {
    override fun buildRequest(filter: DanbooruCommentsFilter) = JsonDanbooruCommentsRequest(filter)
}

open class XmlDanbooruCommentsContext(
    network: suspend (DanbooruCommentsRequest) -> Result<String>
) : DanbooruCommentsContext<XmlDanbooruCommentsRequest>(
    network, { json -> XmlDanbooruCommentsDeserializer().deserializeComments(json) }
) {
    override fun buildRequest(filter: DanbooruCommentsFilter) = XmlDanbooruCommentsRequest(filter)
}
