package post.context

import post.deserialize.PostDeserialize
import post.network.PostRequest

open class PostContext<in Request: PostRequest>(
    private val network: suspend (Request) -> Result<String>,
    private val deserialize: (String) -> Result<PostDeserialize<*>>
) {

    suspend fun get(request: Request): Result<PostDeserialize<*>> {
        val networkResult = network(request)
        if (networkResult.isFailure) return Result.failure(
            networkResult.exceptionOrNull() ?: IllegalStateException("Could not define network exception")
        )

        val networkSuccess = networkResult.getOrNull() ?: throw IllegalStateException("Could not define network result")
        val deserializeResult = deserialize(networkSuccess)
        if (deserializeResult.isFailure) return Result.failure(
            deserializeResult.exceptionOrNull() ?: IllegalStateException("Could not define deserialize exception")
        )

        return Result.success(
            deserializeResult.getOrNull() ?: throw IllegalStateException("Could not define deserialize result")
        )
    }
}
