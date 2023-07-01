package com.makentoshe.booruchan.healthcheck.domain

import com.makentoshe.booruchan.extension.BooruHealthCheckFactory
import javax.inject.Inject

class HealthcheckUseCase @Inject constructor(
    private val repository: HealthcheckRepository,
) {

    suspend operator fun invoke(request: BooruHealthCheckFactory.Request) : Boolean {
        return repository.healthcheck(HealthcheckRequest(request.host)).isAvailable
    }
}