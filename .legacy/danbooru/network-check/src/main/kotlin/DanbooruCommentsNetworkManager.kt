import com.makentoshe.booruchan.core.comment.network.CommentsNetworkManager
import com.makentoshe.booruchan.danbooru.comment.network.DanbooruCommentsRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class DanbooruCommentsNetworkManager(
    private val client: HttpClient
) : CommentsNetworkManager<DanbooruCommentsRequest> {

    private suspend fun internalComments(request: DanbooruCommentsRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getComments(request: DanbooruCommentsRequest): Result<String> = try {
        Result.success(internalComments(request).receive())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
