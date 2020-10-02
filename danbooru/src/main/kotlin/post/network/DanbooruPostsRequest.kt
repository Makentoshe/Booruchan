package post.network

sealed class DanbooruPostsRequest : PostsRequest{
    private val host = "https://danbooru.donmai.us"
    protected val internalUrl = host
}

data class XmlDanbooruPostsRequest(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
    override val url = "${internalUrl}/posts.xml${filter.toUrl()}"
}

data class JsonDanbooruPostsRequest(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
    override val url = "$internalUrl/posts.json${filter.toUrl()}"
}
