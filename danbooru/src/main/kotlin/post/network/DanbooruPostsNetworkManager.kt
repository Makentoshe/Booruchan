package post.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class DanbooruPostsNetworkManager<in Request : DanbooruPostsRequest>(
    private val client: HttpClient
) {
    protected suspend fun internalPosts(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getPosts(request: Request): DanbooruPostsResponse
}

class XmlDanbooruPostsNetworkManager(
    client: HttpClient
) : DanbooruPostsNetworkManager<XmlDanbooruPostsRequest>(client) {

    override suspend fun getPosts(request: XmlDanbooruPostsRequest): XmlDanbooruPostsResponse = try {
        XmlDanbooruPostsResponse.Success(internalPosts(request).receive())
    } catch (e: Exception) {
        XmlDanbooruPostsResponse.Failure(e)
    }
}

class JsonDanbooruPostsNetworkManager(
    client: HttpClient
) : DanbooruPostsNetworkManager<JsonDanbooruPostsRequest>(client) {
    override suspend fun getPosts(request: JsonDanbooruPostsRequest): JsonDanbooruPostsResponse = try {
        JsonDanbooruPostsResponse.Success(internalPosts(request).receive())
    } catch (e: Exception) {
        JsonDanbooruPostsResponse.Failure(e)
    }
}
