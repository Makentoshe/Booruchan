package comment.network

sealed class DanbooruCommentsRequest {

    protected val host = "https://danbooru.donmai.us"

    abstract val url: String

    data class Xml(private val filter: DanbooruCommentsFilter) : DanbooruCommentsRequest() {
        override val url = "$host/comments.xml$filter"
    }

    data class Json(private val filter: DanbooruCommentsFilter) : DanbooruCommentsRequest() {
        override val url = "$host/comments.json$filter"
    }
}
