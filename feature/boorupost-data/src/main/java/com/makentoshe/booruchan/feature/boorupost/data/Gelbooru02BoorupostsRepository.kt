package com.makentoshe.booruchan.feature.boorupost.data

import com.makentoshe.booruchan.feature.BooruSystem
import com.makentoshe.booruchan.feature.boorupost.domain.BoorupostsRepository
import com.makentoshe.booruchan.feature.boorupost.domain.BoorupostsBody
import com.makentoshe.booruchan.feature.boorupost.domain.BoorupostsRequest
import com.makentoshe.booruchan.feature.boorupost.domain.BoorupostsResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class Gelbooru02BoorupostsRepository @Inject constructor(
    private val client: HttpClient,
) : BoorupostsRepository {

    override val supportedBooruSystem: BooruSystem
        get() = BooruSystem.Gelbooru02System


    override suspend fun getPosts(request: BoorupostsRequest): BoorupostsResponse {
        val json = client.get(request.host) {
            url {
                appendPathSegments("index.php")
                parameter("page", "dapi")
                parameter("s", "post")
                parameter("q", "index")

                parameter("json", "1") // force responding with json instead of xml

                parameter("limit", request.count)
                parameter("pid", request.page)
                parameter("tags", request.tags)
            }
        }.bodyAsText()

        return BoorupostsResponse(request, BoorupostsBody.Json(json))
    }
}

fun main() = runBlocking {
    val repository = Gelbooru02BoorupostsRepository(HttpClient(CIO))
    val request = BoorupostsRequest("https://safebooru.org", 10, 1, "hatsune_miku")
    val response = repository.getPosts(request)

    println(response)
}