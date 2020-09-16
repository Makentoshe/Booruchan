package comment.network

sealed class DanbooruCommentRequest {

    protected val host = "https://danbooru.donmai.us"

    abstract val url: String

    data class Xml(private val filter: DanbooruCommentFilter) : DanbooruCommentRequest() {
        override val url = when (filter) {
            is DanbooruCommentFilter.ById -> "$host/comments/${filter.commentId.commentId}.xml"
        }
    }

    data class Json(private val filter: DanbooruCommentFilter) : DanbooruCommentRequest() {
        override val url = when (filter) {
            is DanbooruCommentFilter.ById -> "$host/comments/${filter.commentId.commentId}.json"
        }
    }
}

