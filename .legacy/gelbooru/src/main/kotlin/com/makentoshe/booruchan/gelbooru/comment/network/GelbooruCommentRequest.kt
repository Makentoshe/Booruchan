package com.makentoshe.booruchan.gelbooru.comment.network

import com.makentoshe.booruchan.core.comment.network.CommentRequest
import com.makentoshe.booruchan.gelbooru.network.GelbooruRequest

@Deprecated(message = "This api does not supports", level = DeprecationLevel.ERROR)
object GelbooruCommentRequest: GelbooruRequest(), CommentRequest {
    override val url = host
}