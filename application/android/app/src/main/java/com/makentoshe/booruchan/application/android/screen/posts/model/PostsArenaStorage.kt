package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.application.android.BooruchanDatabase
import com.makentoshe.booruchan.application.android.PostsDeserializeWrapper
import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter

class PostsArenaStorage(
    private val database: BooruchanDatabase,
    private val postsContext: PostsContext<*, *>
) : ArenaStorage<PostsFilter, PostsDeserialize<Post>> {

    override fun fetch(key: PostsFilter): Result<PostsDeserialize<Post>> {
        val postsDeserializeWrapper = database.postsDao().getByFilterUrl(key.toUrl())
            ?: return Result.failure(ArenaStorageException("Could not receive record by key: ${key.toUrl()}"))
        return postsContext.deserialize.invoke(postsDeserializeWrapper.rawValue)
    }

    override fun carry(key: PostsFilter, value: PostsDeserialize<Post>) {
        database.postsDao().insert(PostsDeserializeWrapper(key.toUrl(), value.rawValue))
    }
}