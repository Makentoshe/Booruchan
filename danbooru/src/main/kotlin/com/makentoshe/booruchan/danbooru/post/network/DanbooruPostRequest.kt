package com.makentoshe.booruchan.danbooru.post.network

import com.makentoshe.booruchan.core.post.network.PostRequest

import com.makentoshe.booruchan.danbooru.network.DanbooruRequest

sealed class DanbooruPostRequest: DanbooruRequest(), PostRequest

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

