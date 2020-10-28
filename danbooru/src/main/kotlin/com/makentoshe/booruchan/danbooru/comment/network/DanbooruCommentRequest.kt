package com.makentoshe.booruchan.danbooru.comment.network

import com.makentoshe.booruchan.core.comment.network.CommentRequest

import com.makentoshe.booruchan.danbooru.network.DanbooruRequest

abstract class DanbooruCommentRequest : DanbooruRequest(), CommentRequest

data class XmlDanbooruCommentRequest(private val filter: DanbooruCommentFilter) : DanbooruCommentRequest() {
    override val url = when (filter) {
        is DanbooruCommentFilter.ById -> "$host/comments/${filter.commentId.commentId}.xml"
    }
}

data class JsonDanbooruCommentRequest(private val filter: DanbooruCommentFilter) : DanbooruCommentRequest() {
    override val url = when (filter) {
        is DanbooruCommentFilter.ById -> "$host/comments/${filter.commentId.commentId}.json"
    }
}
