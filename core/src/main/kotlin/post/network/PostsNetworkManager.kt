package post.network

interface PostsNetworkManager<in Request: PostsRequest> {
    suspend fun getPosts(request: Request): Result<String>
}