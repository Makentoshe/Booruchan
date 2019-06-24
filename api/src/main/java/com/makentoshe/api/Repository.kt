package com.makentoshe.api

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.booru.gelbooru.Gelbooru
import com.makentoshe.boorulibrary.booru.safebooru.Safebooru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

interface Repository<K, V> {

    fun get(key: K): V?

    fun wrapCache(cache: Cache<K, V>): Repository<K, V> {
        return RepositoryCache(cache, this)
    }
}

class BooruRepository : Repository<Any, List<Booru>> {
    override fun get(key: Any): List<Booru> {
        return listOf(Gelbooru(), Safebooru())
    }
}

class TagRepository(private val booru: Booru) : Repository<String, Tag> {
    override fun get(key: String) = booru.tagFactory.build(key)
}

class AutocompleteRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<String, List<Tag>> {

    private val autocomplete = booru.getAutocomplete(networkExecutor)

    override fun get(key: String) = autocomplete.request(key)
}

class PostsRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<PostsRequest, List<Post>> {

    private val posts = booru.getPosts(networkExecutor)

    override fun get(key: PostsRequest) = posts.request(key)
}

class PreviewImageRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<Post, ByteArray> {

    private val preview = booru.getPreview(networkExecutor)

    override fun get(key: Post) = preview.request(key)
}

class SampleImageRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<Post, ByteArray> {

    private val sample = booru.getSample(networkExecutor)

    override fun get(key: Post) = sample.request(key)
}

class FileImageRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<Post, ByteArray> {

    private val file = booru.getFile(networkExecutor)

    override fun get(key: Post) = file.request(key)
}

class RepositoryBuilder(private val booru: Booru) {

    fun buildPostRepository(networkExecutor: NetworkExecutor): Repository<PostsRequest, List<Post>> {
        return PostsRepository(booru, networkExecutor)
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