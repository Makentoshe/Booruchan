package com.makentoshe.booruchan.danbooru.comment.network

import com.makentoshe.booruchan.core.comment.network.CommentsRequest

sealed class DanbooruCommentsRequest: CommentsRequest {
    protected val host = "https://danbooru.donmai.us"
}
data class XmlDanbooruCommentsRequest(private val filter: DanbooruCommentsFilter) : DanbooruCommentsRequest() {
    override val url = "$host/comments.xml${filter.toUrl()}"
}

data class JsonDanbooruCommentsRequest(private val filter: DanbooruCommentsFilter) : DanbooruCommentsRequest() {
    override val url = "$host/comments.json${filter.toUrl()}"
}
