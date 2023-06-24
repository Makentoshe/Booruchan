package com.makentoshe.booruchan.core.post.network

interface PostNetworkManager<in Request : PostRequest> {
    suspend fun getPost(request: Request): Result<String>
}

