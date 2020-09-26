package tag.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class DanbooruTagsNetworkManager(
    private val client: HttpClient
): TagsNetworkManager<DanbooruTagsRequest> {

    private suspend fun internalTags(request: DanbooruTagsRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getTags(request: DanbooruTagsRequest): Result<String> = try {
        Result.success(internalTags(request).receive())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
