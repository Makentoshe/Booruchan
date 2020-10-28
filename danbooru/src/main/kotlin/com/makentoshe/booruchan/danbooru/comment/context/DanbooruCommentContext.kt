package com.makentoshe.booruchan.danbooru.comment.context

import com.makentoshe.booruchan.core.comment.context.CommentContext
import com.makentoshe.booruchan.danbooru.comment.deserialize.DanbooruCommentDeserialize
import com.makentoshe.booruchan.danbooru.comment.deserialize.JsonDanbooruCommentDeserializer
import com.makentoshe.booruchan.danbooru.comment.deserialize.XmlDanbooruCommentDeserializer
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentFilter
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentRequest
import com.makentoshe.booruchan.danbooru.comment.network.JsonDanbooruCommentRequest
import com.makentoshe.booruchan.danbooru.comment.network.XmlDanbooruCommentRequest

abstract class DanbooruCommentContext<Request : DanbooruCommentRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<DanbooruCommentDeserialize<*>>
) : CommentContext<Request, DanbooruCommentFilter>(network, deserialize)

open class JsonDanbooruCommentContext(
    network: suspend (JsonDanbooruCommentRequest) -> Result<String>
) : DanbooruCommentContext<JsonDanbooruCommentRequest>(
    network, { json -> JsonDanbooruCommentDeserializer().deserializeComment(json) }
) {
    override fun buildRequest(filter: DanbooruCommentFilter) = JsonDanbooruCommentRequest(filter)
}

open class XmlDanbooruCommentContext(
    network: suspend (XmlDanbooruCommentRequest) -> Result<String>
) : DanbooruCommentContext<XmlDanbooruCommentRequest>(
    network, { xml -> XmlDanbooruCommentDeserializer().deserializeComment(xml) }
) {
    override fun buildRequest(filter: DanbooruCommentFilter) = XmlDanbooruCommentRequest(filter)
}
