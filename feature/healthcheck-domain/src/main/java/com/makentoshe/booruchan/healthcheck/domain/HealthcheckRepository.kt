package com.makentoshe.booruchan.healthcheck.domain

interface HealthcheckRepository {

    suspend fun healthcheck(request: HealthcheckRequest): HealthcheckResponse
}
