package tag.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class DanbooruTagsNetworkManager<in Request : DanbooruTagsRequest, out Response : DanbooruTagsResponse>(
    private val client: HttpClient
) {
    protected suspend fun internalTags(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getTags(request: Request): Response
}

class XmlDanbooruTagsNetworkManager(
    client: HttpClient
) : DanbooruTagsNetworkManager<DanbooruTagsRequest.Xml, XmlDanbooruTagsResponse>(client) {

    override suspend fun getTags(request: DanbooruTagsRequest.Xml): XmlDanbooruTagsResponse = try {
        XmlDanbooruTagsResponse.Success(internalTags(request).receive())
    } catch (e: Exception) {
        XmlDanbooruTagsResponse.Failure(e)
    }
}

class JsonDanbooruTagsNetworkManager(
    client: HttpClient
) : DanbooruTagsNetworkManager<DanbooruTagsRequest.Json, JsonDanbooruTagsResponse>(client) {

    override suspend fun getTags(request: DanbooruTagsRequest.Json): JsonDanbooruTagsResponse = try {
        JsonDanbooruTagsResponse.Success(internalTags(request).receive())
    } catch (e: Exception) {
        JsonDanbooruTagsResponse.Failure(e)
    }
}
