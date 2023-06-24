package com.makentoshe.booruchan.healthcheck.domain

import com.makentoshe.booruchan.feature.context.BooruContext
import javax.inject.Inject

class HealthcheckUseCase @Inject constructor(
    private val repository: HealthcheckRepository,
) {

    suspend operator fun invoke(booruContext: BooruContext) : Boolean {
        return repository.healthcheck(HealthcheckRequest(booruContext.host.url)).isAvailable
    }
}