import com.makentoshe.booruchan.core.post.network.PostsNetworkManager
import com.makentoshe.booruchan.gelbooru.post.network.GelbooruPostsRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GelbooruPostsNetworkManager(
    private val client: HttpClient
): PostsNetworkManager<GelbooruPostsRequest> {

    private suspend fun internalPosts(request: GelbooruPostsRequest): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    override suspend fun getPosts(request: GelbooruPostsRequest): Result<String> = try {
        Result.success(internalPosts(request).receive())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

