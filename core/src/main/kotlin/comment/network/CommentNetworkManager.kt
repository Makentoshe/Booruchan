package comment.network

interface CommentNetworkManager<in Request : CommentRequest> {
    suspend fun getComment(request: Request): Result<String>
}

