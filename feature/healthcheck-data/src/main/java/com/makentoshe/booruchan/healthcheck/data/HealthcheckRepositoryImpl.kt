package com.makentoshe.booruchan.healthcheck.data

import com.makentoshe.booruchan.healthcheck.domain.HealthcheckRepository
import com.makentoshe.booruchan.healthcheck.domain.HealthcheckRequest
import com.makentoshe.booruchan.healthcheck.domain.HealthcheckResponse
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class HealthcheckRepositoryImpl @Inject constructor(
    private val client: HttpClient,
) : HealthcheckRepository {

    override suspend fun healthcheck(request: HealthcheckRequest): HealthcheckResponse {
        return HealthcheckResponse(request, internalHealthcheck(request))
    }

    // Check status code starts with 2..
    private suspend fun internalHealthcheck(request: HealthcheckRequest): Boolean {
        return client.head(request.host).status.value / 100 == 2
    }
}