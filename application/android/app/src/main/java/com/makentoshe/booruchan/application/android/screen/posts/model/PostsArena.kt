package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.core.network.Filter
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.network.PostsRequest

// Common class
abstract class Arena<F : Filter, T>(private val arenaStorage: ArenaStorage<F, T>) {

    internal abstract suspend fun internalSuspendFetch(filter: F): Result<T>

    suspend fun suspendFetch(filter: F): Result<T> = try {
        this.internalSuspendFetch(filter).apply { arenaStorage.carry(filter, this) }
    } catch (exception: Exception) {
        arenaStorage.fetch(filter)
    }
}

// Common class
class PostsArena(
    private val postsContext: PostsContext<PostsRequest, PostsFilter>,
    postsArenaStorage: ArenaStorage<PostsFilter, PostsDeserialize<Post>>
) : Arena<PostsFilter, PostsDeserialize<Post>>(postsArenaStorage) {

    override suspend fun internalSuspendFetch(filter: PostsFilter): Result<PostsDeserialize<Post>> {
        return postsContext.get(postsContext.buildRequest(filter))
    }

}