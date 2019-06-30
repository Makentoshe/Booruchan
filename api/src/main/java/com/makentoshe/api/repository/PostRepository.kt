package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

class PostRepository(booru: Booru, networkExecutor: NetworkExecutor): Repository<Long, Post> {

    private val posts = booru.getPosts(networkExecutor)

    override fun get(key: Long) = posts.request(key)
}
