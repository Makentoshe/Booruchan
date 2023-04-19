package com.makentoshe.booruchan.healthcheck.domain

import com.makentoshe.booruchan.feature.BooruInstance

class HealthcheckUseCase constructor(
    private val repository: HealthcheckRepository,
) {

    suspend operator fun invoke(booruInstance: BooruInstance) : Boolean {
        return repository.healthcheck(HealthcheckRequest(booruInstance.host.url)).isAvailable
    }
}