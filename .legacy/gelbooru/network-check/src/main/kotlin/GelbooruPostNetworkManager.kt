import com.makentoshe.booruchan.core.post.network.PostNetworkManager
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GelbooruPostNetworkManager(
    private val client: HttpClient
) : PostNetworkManager<GelbooruPostRequest> {

    private suspend fun internalPost(request: GelbooruPostRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getPost(request: GelbooruPostRequest): Result<String> = try {
        Result.success(internalPost(request).receive())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
