package com.makentoshe.booruchan.danbooru.comment.network

import com.makentoshe.booruchan.core.comment.network.CommentFilter
import com.makentoshe.booruchan.core.comment.CommentId

// TODO should be finished
// https://danbooru.donmai.us/wiki_pages/help:api
sealed class DanbooruCommentFilter: CommentFilter {

    data class ById(val commentId: CommentId) : DanbooruCommentFilter() {
        override fun toUrl() = commentId.commentId.toString()
    }
}
