package com.makentoshe.booruchan.healthcheck.domain

import com.makentoshe.booruchan.feature.BooruInstance
import javax.inject.Inject

class HealthcheckUseCase @Inject constructor(
    private val repository: HealthcheckRepository,
) {

    suspend operator fun invoke(booruInstance: BooruInstance) : Boolean {
        return repository.healthcheck(HealthcheckRequest(booruInstance.host.url)).isAvailable
    }
}