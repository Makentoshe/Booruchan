import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import post.network.DanbooruPostRequest
import post.network.PostNetworkManager

class DanbooruPostNetworkManager(
    private val client: HttpClient
) : PostNetworkManager<DanbooruPostRequest> {

    private suspend fun internalPost(request: DanbooruPostRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getPost(request: DanbooruPostRequest): Result<String> = try {
        Result.success(internalPost(request).receive())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
