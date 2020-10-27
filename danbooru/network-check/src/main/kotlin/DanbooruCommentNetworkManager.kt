import comment.network.CommentNetworkManager
import comment.network.DanbooruCommentRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class DanbooruCommentNetworkManager(
    private val client: HttpClient
) : CommentNetworkManager<DanbooruCommentRequest> {

    private suspend fun internalComment(request: DanbooruCommentRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getComment(request: DanbooruCommentRequest): Result<String> = try {
        Result.success(internalComment(request).receive())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
