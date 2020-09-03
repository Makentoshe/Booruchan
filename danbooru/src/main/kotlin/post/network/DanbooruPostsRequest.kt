package post.network

sealed class DanbooruPostsRequest {

    abstract val url: String

    data class Xml(
        val count: Int = 100,
        val filter: PostsRequestFilter = PostsRequestFilter()
    ) : DanbooruPostsRequest() {
        override val url = "https://danbooru.donmai.us/posts.xml"
    }

    data class Json(
        val count: Int = 100,
        val filter: PostsRequestFilter = PostsRequestFilter()
    ) : DanbooruPostsRequest() {
        override val url = "https://danbooru.donmai.us/posts.json"
    }
}

