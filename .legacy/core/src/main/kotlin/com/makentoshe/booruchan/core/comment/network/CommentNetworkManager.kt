package com.makentoshe.booruchan.core.comment.network

interface CommentNetworkManager<in Request : CommentRequest> {
    suspend fun getComment(request: Request): Result<String>
}

