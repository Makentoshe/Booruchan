package post.network

sealed class GelbooruPostsRequest {

    abstract val url: String

    data class Xml(
        val count: Int = 100,
        val filter: PostsRequestFilter = PostsRequestFilter()
    ) : GelbooruPostsRequest() {
        override val url = "https://gelbooru.com/index.php?page=dapi&s=post&q=index&limit=$count"
    }

    data class Json(
        val count: Int = 100,
        val filter: PostsRequestFilter = PostsRequestFilter()
    ) : GelbooruPostsRequest() {
        override val url = "https://gelbooru.com/index.php?page=dapi&s=post&q=index&json=1&limit=$count"
    }
}

