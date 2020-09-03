package post.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class DanbooruPostsNetworkManager<in Request : DanbooruPostsRequest, out Response : DanbooruPostsResponse>(
    private val client: HttpClient
) {
    protected suspend fun internalPosts(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getPosts(request: Request): Response
}

class XmlDanbooruPostsNetworkManager(
    client: HttpClient
) : DanbooruPostsNetworkManager<DanbooruPostsRequest.Xml, XmlDanbooruPostsResponse>(client) {

    override suspend fun getPosts(request: DanbooruPostsRequest.Xml): XmlDanbooruPostsResponse = try {
        XmlDanbooruPostsResponse.Success(internalPosts(request).receive())
    } catch (e: Exception) {
        XmlDanbooruPostsResponse.Failure(e)
    }
}

class JsonDanbooruPostsNetworkManager(
    client: HttpClient
) : DanbooruPostsNetworkManager<DanbooruPostsRequest.Json, JsonDanbooruPostsResponse>(client) {
    override suspend fun getPosts(request: DanbooruPostsRequest.Json): JsonDanbooruPostsResponse = try {
        JsonDanbooruPostsResponse.Success(internalPosts(request).receive())
    } catch (e: Exception) {
        JsonDanbooruPostsResponse.Failure(e)
    }
}
