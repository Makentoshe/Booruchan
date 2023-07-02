package com.makentoshe.booruchan.feature

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

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
        }

        return NetworkResponse(request, a.status.value)
    }
}