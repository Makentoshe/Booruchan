package com.makentoshe.booruchan.core.post.network

interface PostsNetworkManager<in Request: PostsRequest> {
    suspend fun getPosts(request: Request): Result<String>
}