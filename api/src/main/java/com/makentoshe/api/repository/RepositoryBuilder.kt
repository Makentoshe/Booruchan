package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

class RepositoryBuilder(private val booru: Booru) {

    fun buildPostsRepository(networkExecutor: NetworkExecutor): Repository<PostsRequest, List<Post>> {
        return PostsRepository(booru, networkExecutor)
    }

    fun buildPostRepository(networkExecutor: NetworkExecutor): Repository<Long, Post> {
        return PostRepository(booru, networkExecutor)
    }

    fun buildPreviewRepository(networkExecutor: NetworkExecutor): Repository<Post, ByteArray> {
        return PreviewImageRepository(booru, networkExecutor)
    }

    fun buildSampleRepository(networkExecutor: NetworkExecutor): Repository<Post, ByteArray> {
        return SampleImageRepository(booru, networkExecutor)
    }

    fun buildFileRepository(networkExecutor: NetworkExecutor): Repository<Post, ByteArray> {
        return FileImageRepository(booru, networkExecutor)
    }
}