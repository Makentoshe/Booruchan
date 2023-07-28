package com.makentoshe.booruchan.feature.network

import com.makentoshe.booruchan.extension.base.network.NetworkMethod
import com.makentoshe.booruchan.extension.base.network.NetworkRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

interface NetworkRepository {

    suspend fun execute(request: NetworkRequest): NetworkResponse
}

class KtorNetworkRepository @Inject constructor(
    private val client: HttpClient,
): NetworkRepository {

    override suspend fun execute(request: NetworkRequest) : NetworkResponse {
        val a = client.request {

            method = when(request.method) {
                NetworkMethod.Get -> HttpMethod.Get
                NetworkMethod.Head -> HttpMethod.Head
            }

            url.takeFrom(request.url)

            request.parameters.forEach { (key, value) -> parameter(key, value) }
        }

        return NetworkResponse(request, a.status.value, NetworkContent(a.body()))
    }
}