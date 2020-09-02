package post.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class GelbooruPostsNetworkManager<in Request : GelbooruPostsRequest, out Response : GelbooruPostsResponse>(
    private val client: HttpClient
) {
    protected suspend fun internalPosts(request: Request): HttpResponse {
        val ktorRequestBuilder = HttpRequestBuilder()
        ktorRequestBuilder.url(request.url)
        return client.get(ktorRequestBuilder)
    }

    abstract suspend fun getPosts(request: Request): Response
}

class XmlGelbooruPostsNetworkManager(
    client: HttpClient
) : GelbooruPostsNetworkManager<GelbooruPostsRequest.Xml, XmlGelbooruPostsResponse>(client) {

    override suspend fun getPosts(request: GelbooruPostsRequest.Xml): XmlGelbooruPostsResponse = try {
        XmlGelbooruPostsResponse.Success(internalPosts(request).receive())
    } catch (e: Exception) {
        XmlGelbooruPostsResponse.Failure(e)
    }
}

class JsonGelbooruPostsNetworkManager(
    client: HttpClient
) : GelbooruPostsNetworkManager<GelbooruPostsRequest.Json, JsonGelbooruPostsResponse>(client) {
    override suspend fun getPosts(request: GelbooruPostsRequest.Json): JsonGelbooruPostsResponse = try {
        JsonGelbooruPostsResponse.Success(internalPosts(request).receive())
    } catch (e: Exception) {
        JsonGelbooruPostsResponse.Failure(e)
    }
}
