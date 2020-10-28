package com.makentoshe.booruchan.core.comment.network

interface CommentsNetworkManager<in Request: CommentsRequest> {
    suspend fun getComments(request: Request): Result<String>
}