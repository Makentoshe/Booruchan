package com.makentoshe.api.repository

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.executor.NetworkExecutor

class PostsRepository(booru: Booru, networkExecutor: NetworkExecutor) : Repository<PostsRequest, List<Post>> {

    private val posts = booru.getPosts(networkExecutor)

    override fun get(key: PostsRequest) = posts.request(key)
}