package com.makentoshe.booruchan.repository.factory

import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository

interface RepositoryFactory {
    fun buildPostsRepository(): Repository<Posts.Request, List<Post>>

    fun buildPreviewsRepository(): Repository<Post, ByteArray>

    fun buildSamplesRepository(): Repository<Post, ByteArray>
}
