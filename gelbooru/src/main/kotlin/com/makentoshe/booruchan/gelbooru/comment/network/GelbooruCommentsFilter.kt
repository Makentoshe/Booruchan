package com.makentoshe.booruchan.gelbooru.comment.network

import com.makentoshe.booruchan.core.comment.network.CommentsFilter
import com.makentoshe.booruchan.core.post.PostId

class GelbooruCommentsFilter(params: Map<String, Any>) : CommentsFilter(params){

    constructor(postId: PostId?) : this(buildMap(postId))

    override fun toUrl(): String {
        if (params.isEmpty()) return ""
        return params.entries.joinToString("") { entry -> "&${entry.key}=${entry.value}" }
    }

    companion object {
        private const val POST_ID = "post_id"

        private fun buildMap(postId: PostId?): Map<String, Any> {
            val params = HashMap<String, Any>()
            if (postId != null) params[POST_ID] = postId.postId
            return params
        }
    }
}
