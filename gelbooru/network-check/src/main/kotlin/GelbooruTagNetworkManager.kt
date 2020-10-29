import com.makentoshe.booruchan.core.tag.network.TagNetworkManager
import com.makentoshe.booruchan.gelbooru.tag.network.GelbooruTagRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GelbooruTagNetworkManager(
    private val client: HttpClient
) : TagNetworkManager<GelbooruTagRequest> {

    private suspend fun internalTag(request: GelbooruTagRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getTag(request: GelbooruTagRequest): Result<String> = try {
        Result.success(internalTag(request).receive())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
