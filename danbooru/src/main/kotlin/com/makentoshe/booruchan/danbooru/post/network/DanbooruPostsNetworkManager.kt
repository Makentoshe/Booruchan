package com.makentoshe.booruchan.danbooru.post.network

import com.makentoshe.booruchan.core.post.network.PostsNetworkManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class DanbooruPostsNetworkManager(
    private val client: HttpClient
): PostsNetworkManager<DanbooruPostsRequest> {

    private suspend fun internalPosts(request: DanbooruPostsRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getPosts(request: DanbooruPostsRequest): Result<String> = try {
        Result.success(internalPosts(request).receive())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

