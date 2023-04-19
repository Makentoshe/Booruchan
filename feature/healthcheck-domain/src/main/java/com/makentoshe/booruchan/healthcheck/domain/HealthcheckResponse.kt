package com.makentoshe.booruchan.healthcheck.domain

data class HealthcheckResponse(
    val request: HealthcheckRequest,
    val isAvailable: Boolean,
)