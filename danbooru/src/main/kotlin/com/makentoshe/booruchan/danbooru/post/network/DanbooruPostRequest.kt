package com.makentoshe.booruchan.danbooru.post.network

import com.makentoshe.booruchan.core.post.network.PostRequest

sealed class DanbooruPostRequest: PostRequest {
    protected val host = "https://danbooru.donmai.us"
}

data class XmlDanbooruPostRequest(private val filter: DanbooruPostFilter) : DanbooruPostRequest() {
    override val url = when (filter) {
        is DanbooruPostFilter.ById -> "$host/posts/${filter.postId.postId}.xml"
    }
}

data class JsonDanbooruPostRequest(private val filter: DanbooruPostFilter) : DanbooruPostRequest() {
    override val url = when (filter) {
        is DanbooruPostFilter.ById -> "$host/posts/${filter.postId.postId}.json"
    }
}

