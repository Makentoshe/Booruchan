package comment.network

import network.GelbooruRequest

sealed class GelbooruCommentRequest: GelbooruRequest() {

    abstract val url: String

    protected val internalUrl = "$host/index.php?page=dapi&s=comment&q=index"
}
