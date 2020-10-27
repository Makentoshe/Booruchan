import comment.network.CommentsNetworkManager
import comment.network.GelbooruCommentsRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GelbooruCommentsNetworkManager(
    private val client: HttpClient
) : CommentsNetworkManager<GelbooruCommentsRequest> {

    private suspend fun internalComments(request: GelbooruCommentsRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getComments(request: GelbooruCommentsRequest): Result<String> = try {
        Result.success(internalComments(request).receive())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
