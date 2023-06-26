package com.makentoshe.booruchan.feature.autocomplete.data.repository

import com.makentoshe.booruchan.feature.autocomplete.domain.repository.AutocompleteRepository
import com.makentoshe.booruchan.feature.autocomplete.domain.repository.request.AutocompleteRequest
import com.makentoshe.booruchan.feature.autocomplete.domain.repository.response.AutocompleteResponse
import com.makentoshe.booruchan.feature.context.BooruSystem
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


//https://safebooru.org/autocomplete.php?q=hatsune
class Gelbooru020AutocompleteRepository @Inject constructor(
    private val client: HttpClient,
): AutocompleteRepository {
    override val supportedBooruSystems: List<BooruSystem>
        get() = listOf(BooruSystem.Gelbooru020System)

    override suspend fun getSearchTags(request: AutocompleteRequest): AutocompleteResponse {
        val json = client.get(request.host) { buildUrl(request) }.bodyAsText()

        return AutocompleteResponse(request, emptyList())
    }

    private fun HttpRequestBuilder.buildUrl(request: AutocompleteRequest) = url {
        appendPathSegments("index.php")
        parameter("page", "autocomplete2")
        parameter("type", "tag_query")
        parameter("q", "index")

        parameter("json", "1") // force responding with json instead of xml

        parameter("limit", 10)
        parameter("term", request.query)
    }

}

fun main() = runBlocking {
//    val parser = Gelbooru02BooruPostsJsonParser()
    val repository = Gelbooru020AutocompleteRepository(HttpClient(CIO))
    val request = AutocompleteRequest("https://gelbooru.com", "hat")
    val response = repository.getSearchTags(request)

    println(response)
}