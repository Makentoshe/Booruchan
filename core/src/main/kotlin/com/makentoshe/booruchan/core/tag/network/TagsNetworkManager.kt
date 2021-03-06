package com.makentoshe.booruchan.core.tag.network

interface TagsNetworkManager<in Request : TagsRequest> {
    suspend fun getTags(request: Request): Result<String>
}