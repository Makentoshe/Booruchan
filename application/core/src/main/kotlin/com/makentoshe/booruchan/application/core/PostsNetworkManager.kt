package com.makentoshe.booruchan.application.core

import com.makentoshe.booruchan.core.post.network.PostsNetworkManager
import com.makentoshe.booruchan.core.post.network.PostsRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Performs a posts networking operations - for the PostsRequest returns
 * the parcelable string or exception wrapped by [Result]
 */
internal class PostsNetworkManager(private val client: HttpClient): PostsNetworkManager<PostsRequest> {

    private suspend fun internalPosts(request: PostsRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getPosts(request: PostsRequest): Result<String> = try {
        Result.success(internalPosts(request).receive())
    } catch (e: Exception) {
        Result.failure(e)
    }
}