package comment.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class DanbooruCommentsNetworkManager<in Request : DanbooruCommentsRequest>(
    private val client: HttpClient
) {
    protected suspend fun internalComments(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getComments(request: Request): DanbooruCommentsResponse
}

class XmlDanbooruCommentsNetworkManager(
    client: HttpClient
) : DanbooruCommentsNetworkManager<DanbooruCommentsRequest.Xml>(client) {

    override suspend fun getComments(request: DanbooruCommentsRequest.Xml): XmlDanbooruCommentsResponse = try {
        XmlDanbooruCommentsResponse.Success(internalComments(request).receive())
    } catch (e: Exception) {
        XmlDanbooruCommentsResponse.Failure(e)
    }
}

class JsonDanbooruCommentsNetworkManager(
    client: HttpClient
) : DanbooruCommentsNetworkManager<DanbooruCommentsRequest.Json>(client) {

    override suspend fun getComments(request: DanbooruCommentsRequest.Json): JsonDanbooruCommentsResponse = try {
        JsonDanbooruCommentsResponse.Success(internalComments(request).receive())
    } catch (e: Exception) {
        JsonDanbooruCommentsResponse.Failure(e)
    }
}
