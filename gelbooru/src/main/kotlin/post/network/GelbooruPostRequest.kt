package post.network

import network.GelbooruRequest

sealed class GelbooruPostRequest: GelbooruRequest() {
    protected val internalUrl = "$host/index.php?page=dapi&s=post&q=index"
}

data class XmlGelbooruPostRequest(private val filter: GelbooruPostFilter) : GelbooruPostRequest() {
    override val url = when (filter) {
        is GelbooruPostFilter.ById -> "$internalUrl&id=${filter.postId}"
    }
}

data class JsonGelbooruPostRequest(private val filter: GelbooruPostFilter) : GelbooruPostRequest() {
    override val url = when (filter) {
        is GelbooruPostFilter.ById -> "$internalUrl&json=1&id=${filter.postId}"
    }
}
