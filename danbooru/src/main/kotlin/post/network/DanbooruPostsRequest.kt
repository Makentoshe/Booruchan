package post.network

sealed class DanbooruPostsRequest {

    abstract val url: String

    data class Xml(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
        override val url = "https://danbooru.donmai.us/posts.xml$filter"
    }

    data class Json(private val filter: DanbooruPostsFilter) : DanbooruPostsRequest() {
        override val url = "https://danbooru.donmai.us/posts.json$filter"
    }
}

