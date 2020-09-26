package tag.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class DanbooruTagNetworkManager(
    private val client: HttpClient
) : TagNetworkManager<DanbooruTagRequest>{

    private suspend fun internalTag(request: DanbooruTagRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getTag(request: DanbooruTagRequest): Result<String> = try {
        Result.success(internalTag(request).receive())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
