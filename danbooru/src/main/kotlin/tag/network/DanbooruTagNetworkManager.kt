package tag.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class DanbooruTagNetworkManager<in Request : DanbooruTagRequest, out Response : DanbooruTagResponse>(
    private val client: HttpClient
) {
    protected suspend fun internalTag(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getTag(request: Request): Response
}

class XmlDanbooruTagNetworkManager(
    client: HttpClient
) : DanbooruTagNetworkManager<DanbooruTagRequest.Xml, XmlDanbooruTagResponse>(client) {

    override suspend fun getTag(request: DanbooruTagRequest.Xml): XmlDanbooruTagResponse = try {
        XmlDanbooruTagResponse.Success(internalTag(request).receive())
    } catch (e: Exception) {
        XmlDanbooruTagResponse.Failure(e)
    }
}

class JsonDanbooruTagNetworkManager(
    client: HttpClient
) : DanbooruTagNetworkManager<DanbooruTagRequest.Json, JsonDanbooruTagResponse>(client) {

    override suspend fun getTag(request: DanbooruTagRequest.Json): JsonDanbooruTagResponse = try {
        JsonDanbooruTagResponse.Success(internalTag(request).receive())
    } catch (e: Exception) {
        JsonDanbooruTagResponse.Failure(e)
    }
}
