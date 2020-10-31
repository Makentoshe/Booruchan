package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.core.network.Filter
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter

// Common class
interface ArenaStorage<F: Filter, T> {
    fun fetch(filter: F): Result<T>

    fun carry(filter: F, result: Result<T>)
}

// Android specific class
class PostsArenaStorage: ArenaStorage<PostsFilter, PostsDeserialize<Post>> {

    override fun fetch(filter: PostsFilter): Result<PostsDeserialize<Post>> {
        return Result.failure(Exception())
    }

    override fun carry(filter: PostsFilter, result: Result<PostsDeserialize<Post>>) {

    }

}