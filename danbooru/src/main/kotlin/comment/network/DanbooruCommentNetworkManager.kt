package comment.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class DanbooruCommentNetworkManager<in Request : DanbooruCommentRequest, out Response : DanbooruCommentResponse>(
    private val client: HttpClient
) {
    protected suspend fun internalComment(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getComment(request: Request): Response
}

class XmlDanbooruCommentNetworkManager(
    client: HttpClient
) : DanbooruCommentNetworkManager<DanbooruCommentRequest.Xml, XmlDanbooruCommentResponse>(client) {

    override suspend fun getComment(request: DanbooruCommentRequest.Xml): XmlDanbooruCommentResponse = try {
        XmlDanbooruCommentResponse.Success(internalComment(request).receive())
    } catch (e: Exception) {
        XmlDanbooruCommentResponse.Failure(e)
    }
}

class JsonDanbooruCommentNetworkManager(
    client: HttpClient
) : DanbooruCommentNetworkManager<DanbooruCommentRequest.Json, JsonDanbooruCommentResponse>(client) {

    override suspend fun getComment(request: DanbooruCommentRequest.Json): JsonDanbooruCommentResponse = try {
        JsonDanbooruCommentResponse.Success(internalComment(request).receive())
    } catch (e: Exception) {
        JsonDanbooruCommentResponse.Failure(e)
    }
}
