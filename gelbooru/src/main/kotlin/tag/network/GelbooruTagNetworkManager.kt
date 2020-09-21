package tag.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class GelbooruTagNetworkManager<in Request : GelbooruTagRequest>(
    private val client: HttpClient
) {
    protected suspend fun internalTag(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getTag(request: Request): GelbooruTagResponse
}

class XmlGelbooruTagNetworkManager(
    client: HttpClient
) : GelbooruTagNetworkManager<XmlGelbooruTagRequest>(client) {

    override suspend fun getTag(request: XmlGelbooruTagRequest): XmlGelbooruTagResponse = try {
        XmlGelbooruTagResponse.Success(internalTag(request).receive())
    } catch (e: Exception) {
        XmlGelbooruTagResponse.Failure(e)
    }
}

class JsonGelbooruTagNetworkManager(
    client: HttpClient
) : GelbooruTagNetworkManager<JsonGelbooruTagRequest>(client) {

    override suspend fun getTag(request: JsonGelbooruTagRequest): JsonGelbooruTagResponse = try {
        JsonGelbooruTagResponse.Success(internalTag(request).receive())
    } catch (e: Exception) {
        JsonGelbooruTagResponse.Failure(e)
    }
}
