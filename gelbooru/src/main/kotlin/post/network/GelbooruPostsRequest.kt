package post.network

import network.GelbooruRequest

sealed class GelbooruPostsRequest: GelbooruRequest(), PostsRequest {
    protected val internalUrl = "$host/index.php?page=dapi&s=post&q=index"
}

data class XmlGelbooruPostsRequest(val filter: GelbooruPostsFilter) : GelbooruPostsRequest() {
    override val url = "$internalUrl$filter"
}

data class JsonGelbooruPostsRequest(val filter: GelbooruPostsFilter) : GelbooruPostsRequest() {
    override val url = "$internalUrl$filter"
}

