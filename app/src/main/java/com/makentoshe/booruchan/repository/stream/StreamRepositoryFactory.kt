package com.makentoshe.booruchan.repository.stream

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.repository.RepositoryFactory
import com.makentoshe.booruchan.repository.cache.CachedRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import org.koin.core.KoinComponent
import org.koin.core.get

class StreamRepositoryFactory(
    private val booru: Booru,
    private val controller: StreamDownloadController? = null
) : RepositoryFactory, KoinComponent {

    override fun buildPostsRepository(): Repository<Posts.Request, List<Post>> {
        val cache = PostInternalCache(get())
        val source = PostsRepository(booru)
        return CachedRepository(cache, source)
    }

    override fun buildPreviewsRepository(): Repository<Post, ByteArray> {
        val streamSource = StreamDownloadRepository(controller, booru)
        val previewStream = StreamDownloadRepositoryDecoratorPreview(streamSource)
        val cache = ImageInternalCache(get(), InternalCache.Type.PREVIEW)
        return CachedRepository(cache, previewStream)
    }

    override fun buildSamplesRepository(): Repository<Post, ByteArray> {
        val streamSource = StreamDownloadRepository(controller, booru)
        val sampleStream = StreamDownloadRepositoryDecoratorSample(streamSource)
        val cache = ImageInternalCache(get(), InternalCache.Type.SAMPLE)
        return CachedRepository(cache, sampleStream)
    }

    override fun buildFilesRepository(): Repository<Post, ByteArray> {
        val streamSource = StreamDownloadRepository(controller, booru)
        val fileStream = StreamDownloadRepositoryDecoratorFile(streamSource)
        val cache = ImageInternalCache(get(), InternalCache.Type.FILE)
        return CachedRepository(cache, fileStream)
    }
}