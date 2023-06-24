package com.makentoshe.booruchan.core.tag.network

interface TagNetworkManager<in Request : TagRequest> {
    suspend fun getTag(request: Request): Result<String>
}
