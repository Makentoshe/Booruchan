package com.makentoshe.booruchan.repository

import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository

/**
 * Interface for describing any repository factories.
 */
interface RepositoryFactory {

    fun buildPostsRepository(): Repository<Posts.Request, List<Post>>

    fun buildPreviewsRepository(): Repository<Post, ByteArray>

    fun buildSamplesRepository(): Repository<Post, ByteArray>

    fun buildFilesRepository(): Repository<Post, ByteArray>
}
