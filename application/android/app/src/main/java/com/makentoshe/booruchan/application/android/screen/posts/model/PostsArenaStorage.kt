package com.makentoshe.booruchan.application.android.screen.posts.model

import com.makentoshe.booruchan.application.core.ArenaStorage
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter

class PostsArenaStorage : ArenaStorage<PostsFilter, PostsDeserialize<Post>> {

    override fun fetch(key: PostsFilter): Result<PostsDeserialize<Post>> {
        return Result.failure(Exception())
    }

    /** Save result to the storage */
    override fun carry(key: PostsFilter, value: Result<PostsDeserialize<Post>>) {

    }
}