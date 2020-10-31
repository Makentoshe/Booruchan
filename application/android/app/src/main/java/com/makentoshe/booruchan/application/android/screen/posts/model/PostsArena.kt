package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.network.PostsRequest

class PostsArena(private val postsContext: PostsContext<PostsRequest, PostsFilter>) {

    suspend fun suspendFetch(filter: PostsFilter): Result<PostsDeserialize<Post>> {
        return postsContext.get(postsContext.buildRequest(filter))
    }

}