package post.network

sealed class DanbooruPostRequest {

    protected val host = "https://danbooru.donmai.us"

    abstract val url: String

    data class Xml(private val filter: DanbooruPostFilter) : DanbooruPostRequest() {
        override val url = when (filter) {
            is DanbooruPostFilter.ById -> "$host/posts/${filter.postId.postId}.xml"
        }
    }

    data class Json(private val filter: DanbooruPostFilter) : DanbooruPostRequest() {
        override val url = when (filter) {
            is DanbooruPostFilter.ById -> "$host/posts/${filter.postId.postId}.xml"
        }
    }
}

