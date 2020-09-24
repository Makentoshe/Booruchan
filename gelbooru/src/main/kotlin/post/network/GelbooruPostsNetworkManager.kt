package post.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class GelbooruPostsNetworkManager<in Request : GelbooruPostsRequest>(
    private val client: HttpClient
) {
    protected suspend fun internalPosts(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getPosts(request: Request): GelbooruPostsResponse
}

class XmlGelbooruPostsNetworkManager(
    client: HttpClient
) : GelbooruPostsNetworkManager<XmlGelbooruPostsRequest>(client) {

    override suspend fun getPosts(request: XmlGelbooruPostsRequest): XmlGelbooruPostsResponse = try {
        XmlGelbooruPostsResponse.Success(internalPosts(request).receive())
    } catch (e: Exception) {
        XmlGelbooruPostsResponse.Failure(e)
    }
}

class JsonGelbooruPostsNetworkManager(
    client: HttpClient
) : GelbooruPostsNetworkManager<JsonGelbooruPostsRequest>(client) {
    override suspend fun getPosts(request: JsonGelbooruPostsRequest): JsonGelbooruPostsResponse = try {
        JsonGelbooruPostsResponse.Success(internalPosts(request).receive())
    } catch (e: Exception) {
        JsonGelbooruPostsResponse.Failure(e)
    }
}
