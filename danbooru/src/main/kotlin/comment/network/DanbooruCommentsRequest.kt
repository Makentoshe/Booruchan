package comment.network

sealed class DanbooruCommentsRequest: CommentsRequest {
    protected val host = "https://danbooru.donmai.us"
}
data class XmlDanbooruCommentsRequest(private val filter: DanbooruCommentsFilter) : DanbooruCommentsRequest() {
    override val url = "$host/comments.xml$filter"
}

data class JsonDanbooruCommentsRequest(private val filter: DanbooruCommentsFilter) : DanbooruCommentsRequest() {
    override val url = "$host/comments.json$filter"
}
