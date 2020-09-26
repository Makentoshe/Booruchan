package tag.network

interface TagNetworkManager<in Request : TagRequest> {
    suspend fun getTag(request: Request): Result<String>
}
