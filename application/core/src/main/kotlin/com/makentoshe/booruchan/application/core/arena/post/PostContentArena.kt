package com.makentoshe.booruchan.application.core.arena.post

import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.application.core.arena.StorageFirstArena
import com.makentoshe.booruchan.core.post.Content
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Arena allows to download and cache posts images (preview, sample or full).
 */
class PostContentArena(
    private val client: HttpClient, postPreviewArena: ArenaStorage<Content, ByteArray>
) : StorageFirstArena<Content, ByteArray>(postPreviewArena) {

    override suspend fun internalSuspendFetch(key: Content): Result<ByteArray> = try {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(key.url)
        val response = client.get<HttpResponse>(ktorRequestBuilder)
        Result.success(response.readBytes())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}