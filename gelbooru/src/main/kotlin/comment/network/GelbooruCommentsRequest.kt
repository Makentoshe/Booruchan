package comment.network

import network.GelbooruRequest

sealed class GelbooruCommentsRequest: GelbooruRequest() {

    protected val internalUrl = "$host/index.php?page=dapi&s=comment&q=index"

    abstract val url: String
}

data class XmlGelbooruCommentsRequest(private val filter: GelbooruCommentsFilter) : GelbooruCommentsRequest() {
    override val url = "$internalUrl${filter.toUrl()}"
}
