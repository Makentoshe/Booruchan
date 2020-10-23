package post.network

import network.DanbooruRequest

sealed class DanbooruPostsRequest : DanbooruRequest(), PostsRequest{
}

data class XmlDanbooruPostsRequest(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
    override val url = "$host/posts.xml${filter.toUrl()}"
}

data class JsonDanbooruPostsRequest(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
    override val url = "$host/posts.json${filter.toUrl()}"
}
