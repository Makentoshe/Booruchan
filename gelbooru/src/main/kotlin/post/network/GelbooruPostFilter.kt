package post.network

import post.PostId

sealed class GelbooruPostFilter {

    data class ById(val postId: PostId): GelbooruPostFilter()
}