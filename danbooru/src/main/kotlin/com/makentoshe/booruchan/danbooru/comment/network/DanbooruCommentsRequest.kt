package com.makentoshe.booruchan.danbooru.comment.network

import com.makentoshe.booruchan.core.comment.network.CommentsRequest

import network.DanbooruRequest

sealed class DanbooruCommentsRequest : DanbooruRequest(), CommentsRequest

data class XmlDanbooruCommentsRequest(private val filter: DanbooruCommentsFilter) : DanbooruCommentsRequest() {
    override val url = "$host/comments.xml${filter.toUrl()}"
}

data class JsonDanbooruCommentsRequest(private val filter: DanbooruCommentsFilter) : DanbooruCommentsRequest() {
    override val url = "$host/comments.json${filter.toUrl()}"
}
