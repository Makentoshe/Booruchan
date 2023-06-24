package com.makentoshe.booruchan.gelbooru.post.network

import com.makentoshe.booruchan.core.post.PostId
import com.makentoshe.booruchan.core.post.network.PostFilter

sealed class GelbooruPostFilter : PostFilter {

    data class ById(val postId: PostId): GelbooruPostFilter() {
        override fun toUrl() = postId.postId.toString()
    }
}