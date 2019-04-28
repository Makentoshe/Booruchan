package com.makentoshe.booruchan.repository.cache

import android.content.Context
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.SampleImageRepository
import com.makentoshe.booruchan.repository.RepositoryFactory

/**
 * Factory for creating repositories with the internal cache.
 */
class CachedRepositoryFactory(
    private val booru: Booru,
    private val context: Context
) : RepositoryFactory {

    override fun buildPostsRepository(): Repository<Posts.Request, List<Post>> {
        val source = PostsRepository(booru)
        val cache = PostInternalCache(context)
        return CachedRepository(cache, source)
    }

    override fun buildPreviewsRepository(): Repository<Post, ByteArray> {
        val source = PreviewImageRepository(booru)
        val cache = ImageInternalCache(context, InternalCache.Type.PREVIEW)
        return CachedRepository(cache, source)
    }

    override fun buildSamplesRepository(): Repository<Post, ByteArray> {
        val source = SampleImageRepository(booru)
        val cache = ImageInternalCache(context, InternalCache.Type.PREVIEW)
        return CachedRepository(cache, source)
    }
}