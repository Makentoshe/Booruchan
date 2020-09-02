package post.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class GelbooruPostNetworkManager<in Request : GelbooruPostRequest, out Response : GelbooruPostResponse>(
    private val client: HttpClient
) {
    protected suspend fun internalPost(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getPost(request: Request): Response
}

class XmlGelbooruPostNetworkManager(
    client: HttpClient
) : GelbooruPostNetworkManager<GelbooruPostRequest.Xml, XmlGelbooruPostResponse>(client) {

    override suspend fun getPost(request: GelbooruPostRequest.Xml): XmlGelbooruPostResponse = try {
        XmlGelbooruPostResponse.Success(internalPost(request).receive())
    } catch (e: Exception) {
        XmlGelbooruPostResponse.Failure(e)
    }
}

class JsonGelbooruPostNetworkManager(
    client: HttpClient
) : GelbooruPostNetworkManager<GelbooruPostRequest.Json, JsonGelbooruPostResponse>(client) {

    override suspend fun getPost(request: GelbooruPostRequest.Json): JsonGelbooruPostResponse = try {
        JsonGelbooruPostResponse.Success(internalPost(request).receive())
    } catch (e: Exception) {
        JsonGelbooruPostResponse.Failure(e)
    }
}
