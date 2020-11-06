package com.makentoshe.booruchan.core.context

import com.makentoshe.booruchan.core.network.Request

abstract class BooruEntityContext<Req: Request, Res>(
    private val network: suspend (Req) -> Result<String>,
    val deserialize: (String) -> Result<Res>
) {

    suspend fun get(request: Req): Result<Res> {
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