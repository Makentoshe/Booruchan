package comment.network

import post.PostId

data class GelbooruCommentsFilter(
    val postId: PostId? = null
) {
    private val params = HashMap<String, Any>()

    init {
        if (postId != null) params[POST_ID] = postId.postId
    }

    fun toUrl(): String {
        if (params.isEmpty()) return ""
        return params.entries.joinToString("") { "&${it.key}=${it.value}" }
    }

    companion object {
        private const val POST_ID = "post_id"
    }
}