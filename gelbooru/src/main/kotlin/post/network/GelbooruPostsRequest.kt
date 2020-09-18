package post.network

import network.GelbooruRequest

sealed class GelbooruPostsRequest: GelbooruRequest() {

    abstract val url: String

    protected val internalUrl = "$host/index.php?page=dapi&s=post&q=index"

    data class Xml(val filter: GelbooruPostsFilter) : GelbooruPostsRequest() {
        override val url = "$internalUrl$filter"
    }

    data class Json(val filter: GelbooruPostsFilter) : GelbooruPostsRequest() {
        override val url = "$internalUrl$filter"
    }
}

