package comment.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class GelbooruCommentsNetworkManager<in Request : GelbooruCommentsRequest>(
    private val client: HttpClient
) {
    protected suspend fun internalComments(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getComments(request: Request): GelbooruCommentsResponse
}

class XmlGelbooruCommentsNetworkManager(
    client: HttpClient
) : GelbooruCommentsNetworkManager<XmlGelbooruCommentsRequest>(client) {

    override suspend fun getComments(request: XmlGelbooruCommentsRequest): XmlGelbooruCommentsResponse = try {
        XmlGelbooruCommentsResponse.Success(internalComments(request).receive())
    } catch (e: Exception) {
        XmlGelbooruCommentsResponse.Failure(e)
    }
}
