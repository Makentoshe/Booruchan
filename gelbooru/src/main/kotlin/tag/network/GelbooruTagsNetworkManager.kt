package tag.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class GelbooruTagsNetworkManager<in Request : GelbooruTagsRequest>(
    private val client: HttpClient
) {
    protected suspend fun internalTags(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getTags(request: Request): GelbooruTagsResponse
}

class XmlGelbooruTagsNetworkManager(
    client: HttpClient
) : GelbooruTagsNetworkManager<XmlGelbooruTagsRequest>(client) {

    override suspend fun getTags(request: XmlGelbooruTagsRequest): XmlGelbooruTagsResponse = try {
        XmlGelbooruTagsResponse.Success(internalTags(request).receive())
    } catch (e: Exception) {
        XmlGelbooruTagsResponse.Failure(e)
    }
}

class JsonGelbooruTagsNetworkManager(
    client: HttpClient
) : GelbooruTagsNetworkManager<JsonGelbooruTagsRequest>(client) {

    override suspend fun getTags(request: JsonGelbooruTagsRequest): JsonGelbooruTagsResponse = try {
        JsonGelbooruTagsResponse.Success(internalTags(request).receive())
    } catch (e: Exception) {
        JsonGelbooruTagsResponse.Failure(e)
    }
}
