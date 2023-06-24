package com.makentoshe.booruchan.application.core.network

import com.makentoshe.booruchan.core.tag.network.TagsNetworkManager
import com.makentoshe.booruchan.core.tag.network.TagsRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Performs a posts networking operations - for the PostsRequest returns
 * the parcelable string or exception wrapped by [Result]
 */
class TagsNetworkManager(private val client: HttpClient): TagsNetworkManager<TagsRequest> {

    override suspend fun getTags(request: TagsRequest): Result<String> = try {
        Result.success(internalPosts(request).receive())
    } catch (e: Exception) {
        Result.failure(e)
    }

    private suspend fun internalPosts(request: TagsRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }
}