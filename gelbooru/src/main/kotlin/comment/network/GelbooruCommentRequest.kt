package comment.network

import network.GelbooruRequest

sealed class GelbooruCommentRequest: GelbooruRequest() {
    protected val internalUrl = "$host/index.php?page=dapi&s=comment&q=index"
}
