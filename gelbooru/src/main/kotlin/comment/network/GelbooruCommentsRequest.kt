package comment.network

import network.GelbooruRequest

sealed class GelbooruCommentsRequest: GelbooruRequest(), CommentsRequest {
    protected val internalUrl = "$host/index.php?page=dapi&s=comment&q=index"
}

data class XmlGelbooruCommentsRequest(private val filter: GelbooruCommentsFilter) : GelbooruCommentsRequest() {
    override val url = "$internalUrl${filter.toUrl()}"
}
