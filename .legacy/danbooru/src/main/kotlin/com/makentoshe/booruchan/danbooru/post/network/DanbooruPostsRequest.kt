package com.makentoshe.booruchan.danbooru.post.network

import com.makentoshe.booruchan.core.post.network.PostsRequest

sealed class DanbooruPostsRequest : PostsRequest {
    private val host = "https://danbooru.donmai.us"
    protected val internalUrl = host
}

data class XmlDanbooruPostsRequest(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
    override val url = "${internalUrl}/posts.xml${filter.toUrl()}"
}

data class JsonDanbooruPostsRequest(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
    override val url = "$internalUrl/posts.json${filter.toUrl()}"
}
