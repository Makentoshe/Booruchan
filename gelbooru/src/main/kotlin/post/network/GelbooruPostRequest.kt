package post.network

import network.GelbooruRequest

sealed class GelbooruPostRequest: GelbooruRequest() {

    abstract val url: String

    protected val filterlessUrl = "$host/index.php?page=dapi&s=post&q=index"

    data class Xml(private val filter: GelbooruPostFilter) : GelbooruPostRequest() {
        override val url = when (filter) {
            is GelbooruPostFilter.ById -> "$filterlessUrl&id=${filter.postId}"
        }
    }

    data class Json(private val filter: GelbooruPostFilter) : GelbooruPostRequest() {
        override val url = when (filter) {
            is GelbooruPostFilter.ById -> "$filterlessUrl&json=1&id=${filter.postId}"
        }
    }
}