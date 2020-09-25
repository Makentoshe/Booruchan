package post.network

sealed class DanbooruPostsRequest {
    private val host = "https://danbooru.donmai.us"
    protected val internalUrl = host
    abstract val url: String
}

data class XmlDanbooruPostsRequest(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
    override val url = "${internalUrl}/posts.xml$filter"
}

data class JsonDanbooruPostsRequest(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
    override val url = "$internalUrl/posts.json$filter"
}
