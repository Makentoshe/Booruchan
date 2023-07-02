package com.makentoshe.booruchan.feature

interface NetworkRepository {

    suspend fun execute(request: NetworkRequest): NetworkResponse
}
