package com.makentoshe.booruchan.feature.boorupost.data

import com.makentoshe.booruchan.feature.context.BooruSystem
import com.makentoshe.booruchan.feature.boorupost.domain.repository.BoorupostsRepository
import com.makentoshe.booruchan.feature.boorupost.domain.repository.request.BoorupostsRequest
import com.makentoshe.booruchan.feature.boorupost.domain.repository.response.BoorupostsResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class Gelbooru02BoorupostsRepository @Inject constructor(
    private val client: HttpClient,
    private val parser: Gelbooru02BooruPostsJsonParser,
) : BoorupostsRepository {

    override val supportedBooruSystem: BooruSystem
        get() = BooruSystem.Gelbooru02System

    override suspend fun getPosts(request: BoorupostsRequest): BoorupostsResponse {
        val json = client.get(request.host) { buildUrl(request) }.bodyAsText()

        return BoorupostsResponse(request, parser.parse(json))
    }

    private fun HttpRequestBuilder.buildUrl(request: BoorupostsRequest) = url {
        appendPathSegments("index.php")
        parameter("page", "dapi")
        parameter("s", "post")
        parameter("q", "index")

        parameter("json", "1") // force responding with json instead of xml

        parameter("limit", request.count)
        parameter("pid", request.page)
        parameter("tags", request.tags)
    }
}

fun main() = runBlocking {
    val parser = Gelbooru02BooruPostsJsonParser()
    val repository = Gelbooru02BoorupostsRepository(HttpClient(CIO), parser)
    val request = BoorupostsRequest("https://gelbooru.com", 1, 1, "hatsune_miku")
    val response = repository.getPosts(request)

    println(response)
}