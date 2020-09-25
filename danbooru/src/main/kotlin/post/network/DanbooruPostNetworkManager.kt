package post.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class DanbooruPostNetworkManager<in Request : DanbooruPostRequest, out Response : DanbooruPostResponse>(
    private val client: HttpClient
) {
    protected suspend fun internalPost(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getPost(request: Request): Response
}

class XmlDanbooruPostNetworkManager(
    client: HttpClient
) : DanbooruPostNetworkManager<XmlDanbooruPostRequest, XmlDanbooruPostResponse>(client) {

    override suspend fun getPost(request: XmlDanbooruPostRequest): XmlDanbooruPostResponse = try {
        XmlDanbooruPostResponse.Success(internalPost(request).receive())
    } catch (e: Exception) {
        XmlDanbooruPostResponse.Failure(e)
    }
}

class JsonDanbooruPostNetworkManager(
    client: HttpClient
) : DanbooruPostNetworkManager<JsonDanbooruPostRequest, JsonDanbooruPostResponse>(client) {

    override suspend fun getPost(request: JsonDanbooruPostRequest): JsonDanbooruPostResponse = try {
        JsonDanbooruPostResponse.Success(internalPost(request).receive())
    } catch (e: Exception) {
        JsonDanbooruPostResponse.Failure(e)
    }
}
