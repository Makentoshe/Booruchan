package post.network

import post.PostId

sealed class GelbooruPostFilter : PostFilter {

    data class ById(val postId: PostId): GelbooruPostFilter() {
        override fun toUrl() = postId.postId.toString()
    }
}